package by.get.pms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Controller
public class IndexController {

	@RequestMapping(value = WebConstants.DEFAULT_PAGE, method = RequestMethod.GET)
	public String index() {
		return "redirect:" + WebConstants.PROJECTS_URL;
	}
}
