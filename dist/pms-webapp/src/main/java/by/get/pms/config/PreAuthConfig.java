package by.get.pms.config;

import by.get.pms.security.ApplicationAuthenticationProvider;
import by.get.pms.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Configuration
public class PreAuthConfig {

	@Autowired
	private AuthorizationService authorizationService;

	@Bean
	public AuthenticationProvider authenticationProvider() {
		System.setProperty(SecurityContextHolder.SYSTEM_PROPERTY, "ApplicationSecurityContextHolderStrategy");

		final ApplicationAuthenticationProvider preAuthenticationProvider = new ApplicationAuthenticationProvider();
		preAuthenticationProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());

		return preAuthenticationProvider;
	}

	@Bean
	public UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
		UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
		wrapper.setUserDetailsService(authorizationService);
		return wrapper;
	}
}
