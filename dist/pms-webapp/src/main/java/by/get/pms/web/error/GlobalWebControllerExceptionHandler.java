package by.get.pms.web.error;


import by.get.pms.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Milos.Savic on 10/6/2016.
 */
@ControllerAdvice(annotations = Controller.class)
public class GlobalWebControllerExceptionHandler extends BaseErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalWebControllerExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	public ModelAndView exception(Exception ex, HttpServletRequest req) {
		LOGGER.error("Unexpected error occurred", ex);

		final ModelAndView modelAndView = new ModelAndView("error/general");
		Throwable rootCause = ThrowableUtil.getRootCause(ex);
		logErrorMessage(ex, req);
		if (rootCause instanceof ApplicationException) {
			Object[] params = ((ApplicationException) rootCause).getParams();
			String msg = buildMessage(rootCause.getClass().getName(), params);
			modelAndView.addObject("errorMessage", msg);
		} else {
			modelAndView.addObject("errorMessage", rootCause);
		}
		return modelAndView;
	}
}
