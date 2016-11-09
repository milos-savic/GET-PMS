package by.get.pms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by Milos.Savic on 10/12/2016.
 */
public class PreAuthRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter {

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String userName = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){

			if(authentication instanceof AnonymousAuthenticationToken) return null;

			OAuth2Authentication oauth = (OAuth2Authentication) authentication;
			userName = (String) oauth.getPrincipal();
		}

		return userName;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if(this.logger.isDebugEnabled()) {
			this.logger.debug("Checking secure context token: " + SecurityContextHolder.getContext().getAuthentication());
		}

		if(getPreAuthenticatedPrincipal((HttpServletRequest)request) != null) {
			authenticationProvider.authenticate(SecurityContextHolder.getContext().getAuthentication());
		}

		chain.doFilter(request, response);
	}
}
