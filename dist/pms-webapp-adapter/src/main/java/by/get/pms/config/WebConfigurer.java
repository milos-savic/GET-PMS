package by.get.pms.config;

import by.get.pms.listener.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer {

	private final Logger logger = LoggerFactory.getLogger(WebConfigurer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		initH2Console(servletContext);
		servletContext.addListener(new SessionListener());
		logger.info("Web application fully configured");
	}

	/**
	 * Initializes H2 console
	 */
	private void initH2Console(ServletContext servletContext) {
		logger.debug("Initialize H2 console");
		ServletRegistration.Dynamic h2ConsoleServlet = servletContext
				.addServlet("H2Console", new org.h2.server.web.WebServlet());
		h2ConsoleServlet.addMapping("/h2-console/*");
		h2ConsoleServlet.setInitParameter("-properties", "~/h2db/pms;AUTO_SERVER=TRUE;");
		h2ConsoleServlet.setLoadOnStartup(1);
	}
}
