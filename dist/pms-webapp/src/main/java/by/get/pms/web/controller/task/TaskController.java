package by.get.pms.web.controller.task;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.security.Application;
import by.get.pms.service.project.ProjectFacade;
import by.get.pms.service.task.TaskFacade;
import by.get.pms.validation.Admin;
import by.get.pms.validation.Developer;
import by.get.pms.validation.ProjectManager;
import by.get.pms.web.controller.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by milos on 23-Oct-16.
 */
@Controller
public class TaskController {

	@Autowired
	private ProjectFacade projectFacade;

	@Autowired
	private TaskFacade taskFacade;

	@RequestMapping(value = WebConstants.TASKS_URL + "/{project}", method = RequestMethod.GET)
	public ModelAndView getProjectTasks(@PathVariable("project") String project) {
		Long projectId = Long.valueOf(project);
		ProjectDTO projectDTO = projectFacade.getProject(projectId);

		ModelAndView modelAndView = new ModelAndView(WebConstants.TASKS_HTML_PATH);
		List<TaskDTO> projectTasks = retrieveProjectTasks(projectDTO, Application.getInstance().getUser());
		modelAndView.getModel().put("projectTasks", projectTasks);

		return modelAndView;
	}

	private List<TaskDTO> retrieveProjectTasks(ProjectDTO project, UserDTO user) {
		switch (user.getRole()) {
		case ADMIN:
			return adminProjectTasks(project, user);
		case PROJECT_MANAGER:
			return projectManagerProjectTasks(project, user);
		case DEV:
			return developerProjectTasks(project, user);
		default:
			throw new RuntimeException("Unrecognized user role: " + user.getRole().name());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN_USER')")
	private List<TaskDTO> adminProjectTasks(ProjectDTO project, @Admin UserDTO admin) {
		return taskFacade.getProjectTasksAvailableForAdmin(project, admin);
	}

	@PreAuthorize("hasRole('ROLE_PROJECT_MANAGER_USER')")
	private List<TaskDTO> projectManagerProjectTasks(ProjectDTO project, @ProjectManager UserDTO projectManager) {
		return taskFacade.getProjectTasksAvailableForPM(project, projectManager);
	}

	@PreAuthorize("hasRole('ROLE_DEV_USER')")
	private List<TaskDTO> developerProjectTasks(ProjectDTO project, @Developer UserDTO developer) {
		return taskFacade.getProjectTasksAvailableForDeveloper(project, developer);
	}
}
