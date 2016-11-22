package by.get.pms.security;

import by.get.pms.model.UserAccount;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

/**
 * Created by milos on 20-Nov-16.
 */
public interface SocialAuthenticationService extends UserDetailsService, ApplicationListener {

    /**
     * Finds and grants authorities for given user account.
     *
     * @param userAccount - UserAccount object
     * @return set of credentials for given user account
     */
    Set<GrantedAuthority> grantAuthorities(UserAccount userAccount);

}
