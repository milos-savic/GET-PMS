package by.get.pms.web.controller.signin;

import by.get.pms.web.controller.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Controller
public class SigninController {

	@RequestMapping(value = WebConstants.SIGNIN_PAGE)
	public String signin() {
		return "signin/signin";
	}
}
