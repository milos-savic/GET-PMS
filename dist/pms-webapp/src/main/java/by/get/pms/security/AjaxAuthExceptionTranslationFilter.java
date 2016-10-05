package by.get.pms.security;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by milos.savic on 10/5/2016.
 */
public class AjaxAuthExceptionTranslationFilter extends ExceptionTranslationFilter {

	private static final String UNAUTHORIZED_MSG = "User session expired or not logged in yet. Please login.";
	private static final Logger LOGGER = LoggerFactory.getLogger(AjaxAuthExceptionTranslationFilter.class);

	public AjaxAuthExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationEntryPoint);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// This filter is only for AJAX requests
		final String ajaxParam = req.getParameter("ajax");
		final String header = ((HttpServletRequest) req).getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(header) || BooleanUtils.toBoolean(ajaxParam);
		if (isAjax) {
			// use this filter
			LOGGER.trace("Ajax call detected");
			super.doFilter(req, res, chain);
		} else {
			// skip this filter
			chain.doFilter(req, res);
		}
	}

	@Override
	public void afterPropertiesSet() {
		// empty
	}

	@Override
	protected void sendStartAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			AuthenticationException reason) throws ServletException, IOException {
		processResponse(response, UNAUTHORIZED_MSG, HttpServletResponse.SC_UNAUTHORIZED);
	}

	private class AjaxAccessDeniedHandlerImpl extends AccessDeniedHandlerImpl {

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response,
				AccessDeniedException accessDeniedException) throws IOException, ServletException {
			String msg = "You are not authorized to do this.";
			processResponse(response, msg, HttpServletResponse.SC_FORBIDDEN);
		}
	}

	private void processResponse(HttpServletResponse response, String msg, int status) throws IOException {
		LOGGER.info(msg);
		response.getWriter().println(msg);
		response.setStatus(status);
	}

	public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

		public AjaxAuthenticationEntryPoint() {
			super();
		}

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
				throws IOException, ServletException {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_MSG);
		}
	}
}
