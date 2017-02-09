package by.get.pms.web.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Milos.Savic on 10/6/2016.
 */
@Controller
public class CustomErrorController extends BaseErrorController implements ErrorController {

	private static final String PATH = "/error";

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomErrorController.class);

	@Override
	public String getErrorPath() {
		return PATH;
	}

	@RequestMapping(value = PATH)
	public String generalError(HttpServletRequest req, Model model) {
		Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
		LOGGER.error("Unexpected error occurred!", throwable);

		String message = logErrorMessage(throwable, req);

		model.addAttribute("errorMessage", message);
		return "error/general";
	}
}
