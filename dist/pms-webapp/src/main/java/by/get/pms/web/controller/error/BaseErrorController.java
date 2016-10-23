package by.get.pms.web.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Milos.Savic on 10/6/2016.
 */
public class BaseErrorController {

	private static final String ERROR_MSG_KEY = "error.description";

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseErrorController.class);

	@Autowired
	private MessageSource messageSource;

	public String logErrorMessage(Throwable throwable, HttpServletRequest request) {
		// retrieve some useful information from the request
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String exceptionMessage = getExceptionMessage(throwable, statusCode);
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown URI";
		}

		String msg = buildMessage(ERROR_MSG_KEY, new Object[] { statusCode, requestUri, exceptionMessage });

		LOGGER.error(msg);

		return msg;
	}

	public String buildMessage(String messageKey, Object[] messageParams) {
		String msg;
		try {
			msg = messageSource.getMessage(messageKey, messageParams, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			LOGGER.warn("Message key '{}' with parameters '{}' wasn't found in messeage.properties!!", messageKey,
					messageParams);
			msg = messageSource.getMessage("unknown.error", messageParams, LocaleContextHolder.getLocale());
		}
		return msg;
	}

	private String getExceptionMessage(Throwable throwable, Integer statusCode) {
		if (throwable != null) {
			return ThrowableUtil.getRootCause(throwable).getMessage();
		}
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		return httpStatus.getReasonPhrase();
	}
}
