package by.get.pms.security;

import by.get.pms.dataaccess.UserAccountRepository;
import by.get.pms.dto.UserDTO;
import by.get.pms.model.UserAccount;
import by.get.pms.model.UserRole;
import by.get.pms.service.user.UserService;
import by.get.pms.utility.Transformers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Service
public class OAuth2AuthenticationServiceImpl implements AuthenticationService {

	protected static final Log LOGGER = LogFactory.getLog(OAuth2AuthenticationServiceImpl.class);

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails authenticate(Authentication authentication) {

		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
		String username = (String) oAuth2Authentication.getPrincipal();
		UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username);

		if (userAccount != null && !(userAccount.isActive())) {
			throw new LockedException("User account is locked");
		}

		if (userAccount == null) {
			Map<String, String> userDetails = (Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails();
			String name = userDetails.get("name");
			String firstName = name.split(" ")[0];
			String lastName = name.split(" ")[1];
			String email = userDetails.get("email");

			UserDTO userParams = new UserDTO(-1L, firstName, lastName, email, username,
					LocalDateTime.now(), true, UserRole.ROLE_GUEST);
			userService.createUser(userParams);
			userAccount = userAccountRepository.findUserAccountByUsername(username);
		}
		UserDetails userDetails = createUserDetails(userAccount);

		if (userDetails.getAuthorities().size() == 0) {
			throw new CredentialsExpiredException("No active role available");
		}
		return userDetails;
	}

	private UserDetails createUserDetails(UserAccount userAccount) {
		boolean accountNonExpired = true;
		boolean isCredentialsNotExpired = true;
		final Set<GrantedAuthority> granted = grantAuthorities(userAccount);
		boolean isActive = userAccount.isActive();

		final User user = new User(userAccount.getUserName(), "", isActive, accountNonExpired, isCredentialsNotExpired,
				isActive, granted);
		user.setDisplayName(userAccount.getUser().getFirstName() + " " + userAccount.getUser().getLastName());
		return user;
	}

	@Override
	public Set<GrantedAuthority> grantAuthorities(UserAccount userAccount) {
		final Set<GrantedAuthority> granted = new HashSet<>();

		final UserRole currentRole = userAccount.getRole();

		if (currentRole != null) {

			Application.getInstance()
					.setCredentials(Transformers.USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(userAccount.getUser()),
							currentRole);
			granted.add(new SimpleGrantedAuthority(currentRole.name()));
		}

		return granted;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if (applicationEvent instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) applicationEvent;
			final String userName = event.getAuthentication().getName();
			LOGGER.info(String.format("User %s has been successfully authenticated.", userName));
		} else if (applicationEvent instanceof AuthorizationFailureEvent) {
			AuthorizationFailureEvent event = (AuthorizationFailureEvent) applicationEvent;
			final String userName = ((AuthorizationFailureEvent) applicationEvent).getAuthentication().getName();
			LOGGER.info(
					String.format("Unauthorized access to %s has been detected by %s.", event.getSource(), userName));
		}
	}
}
