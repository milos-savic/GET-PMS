package by.get.pms.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

/**
 * Created by Milos.Savic on 10/12/2016.
 */
public class ApplicationAuthenticationProvider extends PreAuthenticatedAuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final Authentication successAuthentication = super.authenticate(authentication);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return successAuthentication;
	}
}
