package by.get.pms.security;

import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Milos.Savic on 10/12/2016.
 */
public class PreAuthRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter {

	public PreAuthRequestHeaderAuthenticationFilter() {
		super();
		//TODO Pull this value from a properties file (application.properties, or localstrings.properties)
		this.setPrincipalRequestHeader("SM_USER");
	}

	/**
	 * This is called when a request is made, the returned object identifies the
	 * user and will either be {@literal null} or a String. This method will throw an exception if
	 * exceptionIfHeaderMissing is set to true (default) and the required header is missing.
	 *
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String userName = (String) (super.getPreAuthenticatedPrincipal(request));

		return userName;
	}

}
