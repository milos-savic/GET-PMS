package by.get.pms.web.controller.user;

import by.get.pms.dto.UserDTO;
import by.get.pms.service.user.UserFacade;
import by.get.pms.web.controller.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by milos on 20-Oct-16.
 */
@Controller
public class UserManagementController {

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = WebConstants.USER_MANAGEMENT_URL, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
    public ModelAndView getUsers() {
        final ModelAndView modelAndView = new ModelAndView(WebConstants.USER_MANAGEMENT_INDEX_HTML_PATH);
        final List<UserDTO> allUsers = userFacade.getAllUsers();
        modelAndView.getModel().put("users", allUsers);
        return modelAndView;
    }

}
