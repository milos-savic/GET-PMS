package by.get.pms.web.controller.project;

import by.get.pms.acl.ProjectACL;
import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.UserDTO;
import by.get.pms.dtos.UserRole;
import by.get.pms.facade.project.ProjectFacade;
import by.get.pms.facade.user.UserFacade;
import by.get.pms.security.Application;
import by.get.pms.web.controller.WebConstants;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/26/2016.
 */
@Controller
public class ProjectController {

	@Autowired
	private ProjectFacade projectFacade;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private ProjectACL projectACL;

	@RequestMapping(value = WebConstants.PROJECTS_URL, method = RequestMethod.GET)
	public ModelAndView getProjects() {
		ModelAndView modelAndView = new ModelAndView(WebConstants.PROJECTS_HTML_PATH);

		UserDTO loggedInUser = Application.getInstance().getUser();

		List<ProjectDTO> projects = projectACL.retrieveProjectsBasedOnACL();

		modelAndView.getModel().put("projects", projects);

		modelAndView.getModel().put("projectManagers", retrieveProjectManagers(loggedInUser));
		return modelAndView;
	}

	private List<UserDTO> retrieveProjectManagers(UserDTO loggedInUser) {
		switch (loggedInUser.getRole()) {
		case ROLE_ADMIN:
			return userFacade.getAllUsers().parallelStream()
					.filter(userDTO -> userDTO.getRole().equals(UserRole.ROLE_PROJECT_MANAGER))
					.collect(Collectors.toList());
		case ROLE_PROJECT_MANAGER:
			return Lists.newArrayList(loggedInUser);
		case ROLE_DEV:
			return Lists.newArrayList();
		case ROLE_GUEST:
			return Lists.newArrayList();
		default:
			throw new RuntimeException("Unsupported role: " + loggedInUser.getRole());
		}
	}
}
