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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    protected static final Log LOGGER = LogFactory.getLog(AuthenticationServiceImpl.class);

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username);

        if (userAccount != null && !(userAccount.isActive())) {
            throw new LockedException("User account is locked");
        }

        if (userAccount == null) {
            UserDTO userParams = new UserDTO(-1L, username, username, "dummy.mail@mail.com", username, LocalDateTime.now(), true, UserRole.GUEST);
            userService.createUser(userParams);
            userAccount = userAccountRepository.findUserAccountByUsername(username);
        }
        UserDetails userDetails = createUserDetails(userAccount);

        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(userDetails.getAuthorities());
        if (authorities.size() == 0) {
            throw new CredentialsExpiredException("No active role available");
        }
        return userDetails;
    }

    private UserDetails createUserDetails(UserAccount userAccount) {
        boolean accountNonExpired = true;
        boolean isCredentialsNotExpired = true;
        final Set<GrantedAuthority> granted = grantAuthorities(userAccount);
        boolean isActive = userAccount.isActive();

        final User user = new User(userAccount.getUserName(), null, isActive, accountNonExpired,
                isCredentialsNotExpired, isActive, granted);
        user.setDisplayName(userAccount.getUser().getFirstName() + " " + userAccount.getUser().getLastName());
        return user;
    }

    @Override
    public Set<GrantedAuthority> grantAuthorities(UserAccount userAccount) {
        final Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();

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
