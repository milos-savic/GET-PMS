package by.get.pms.web.error;

import by.get.pms.exception.ApplicationException;
import by.get.pms.web.response.Response;
import by.get.pms.web.response.ResponseBuilderFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Milos.Savic on 10/6/2016.
 */
public class GlobalRestControllerExceptionHandler extends BaseErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRestControllerExceptionHandler.class);

	@Autowired
	private ResponseBuilderFactoryBean responseBuilder;

	@ExceptionHandler(Exception.class)
	@ResponseBody
	private Response handleBadRequest(Exception ex, HttpServletRequest req) {
		LOGGER.error("Unexpected error occurred!", ex);
		logErrorMessage(ex, req);
		if (ex instanceof ApplicationException) {
			return responseBuilder.instance().addError(ex, ((ApplicationException) ex).getParams()).build();
		} else {
			return responseBuilder.instance().addError(ex).build();
		}
	}
}
