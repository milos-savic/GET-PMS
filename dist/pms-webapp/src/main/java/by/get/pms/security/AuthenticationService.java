package by.get.pms.security;

import by.get.pms.model.UserAccount;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * Responsible for user authorization withing Spring Security and application.
 * <p>
 * Created by Milos.Savic on 10/12/2016.
 */
public interface AuthenticationService extends ApplicationListener {

	UserDetails authenticate(Authentication authentication);

	/**
	 * Finds and grants rights for given user account.
	 *
	 * @param userAccount - UserAccount object
	 * @return set of credentials for given user account
	 */
	Set<GrantedAuthority> grantAuthorities(UserAccount userAccount);

}
