package by.get.pms.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by milos.savic on 10/5/2016.
 */
public class SessionListener implements HttpSessionListener {

	private final Logger logger = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		logger.info("Session is created: {}", httpSessionEvent.getSession());
		httpSessionEvent.getSession().setMaxInactiveInterval(-1);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		logger.info("Session is destroyed: {}", httpSessionEvent.getSession());
	}
}
