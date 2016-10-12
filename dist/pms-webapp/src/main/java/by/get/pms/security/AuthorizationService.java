package by.get.pms.security;

import by.get.pms.model.UserAccount;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Set;

/**
 *  Responsible for user authorization withing Spring Security and application.
 *
 * Created by Milos.Savic on 10/12/2016.
 */
public interface AuthorizationService extends UserDetailsService, ApplicationListener {

	/**
	 * Finds and grants rights for given user account.
	 *
	 * @param userAccount - UserAccount object
	 * @return set of credentials for given user account
	 */
	public Set<GrantedAuthority> grantAuthorities(UserAccount userAccount);

}
