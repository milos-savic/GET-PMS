package by.get.pms.security;

import by.get.pms.dtos.UserDTO;
import by.get.pms.dtos.UserRole;
import by.get.pms.facade.user.UserFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by milos on 20-Nov-16.
 */
@Service
public class SocialAuthenticationServiceImpl implements SocialAuthenticationService {

    private static final Log LOGGER = LogFactory.getLog(SocialAuthenticationServiceImpl.class);

    @Autowired
    private UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

        UserDTO userDTO = userFacade.getUserByUserName(username);

        if (userDTO != null && !(userDTO.getActive())) {
            throw new LockedException("User account is locked");
        }

        if (userDTO == null) {
            throw new UsernameNotFoundException("No user account registered in the system");
        }
        UserDetails userDetails = createUserDetails(userDTO);

        final List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
        if (authorities.size() == 0) {
            throw new CredentialsExpiredException("No active role available");
        }
        return userDetails;
    }

    private UserDetails createUserDetails(UserDTO userDTO) {
        boolean accountNonExpired = true;
        boolean isCredentialsNotExpired = true;
        final Set<GrantedAuthority> granted = grantAuthorities(userDTO);
        boolean isActive = userDTO.getActive();

        final User user = new User(userDTO.getUserName(), "", isActive, accountNonExpired, isCredentialsNotExpired,
                isActive, granted);
        user.setDisplayName(userDTO.getFirstName() + " " + userDTO.getLastName());
        return user;
    }

    @Override
    public Set<GrantedAuthority> grantAuthorities(UserDTO userDTO) {
        final Set<GrantedAuthority> granted = new HashSet<>();

        final UserRole currentRole = userDTO.getRole();

        if (currentRole != null) {

            Application.getInstance().setCredentials(userDTO, currentRole);
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
