package by.get.pms.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created by Milos.Savic on 10/12/2016.
 */
public class ApplicationAuthenticationProvider implements AuthenticationProvider {
	private static final Log logger = LogFactory.getLog(ApplicationAuthenticationProvider.class);

	@Autowired
	private AuthenticationService oAuth2AuthenticationService;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!this.supports(authentication.getClass())) {
			return null;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("PreAuthenticated authentication request: " + authentication);
			}

			if (authentication.getPrincipal() == null) {
				logger.debug("No pre-authenticated principal found in request.");
				throw new BadCredentialsException("No pre-authenticated principal found in request.");
			} else {
				UserDetails ud = oAuth2AuthenticationService.authenticate(authentication);
				PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(
						ud, null, ud.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(preAuthenticatedAuthenticationToken);

				return preAuthenticatedAuthenticationToken;
			}
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2Authentication.class.isAssignableFrom(authentication);
	}
}
