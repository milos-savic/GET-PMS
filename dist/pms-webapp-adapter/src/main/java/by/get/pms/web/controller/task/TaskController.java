package by.get.pms.web.controller.task;

import by.get.pms.acl.TaskACL;
import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.TaskDTO;
import by.get.pms.dtos.UserDTO;
import by.get.pms.dtos.TaskStatus;
import by.get.pms.dtos.UserRole;
import by.get.pms.facade.project.ProjectFacade;
import by.get.pms.facade.task.TaskFacade;
import by.get.pms.facade.user.UserFacade;
import by.get.pms.web.controller.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by milos on 23-Oct-16.
 */
@Controller
public class TaskController {

	@Autowired
	private ProjectFacade projectFacade;

	@Autowired
	private TaskFacade taskFacade;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private TaskACL taskACL;

	@RequestMapping(value = WebConstants.TASKS_URL + "/{project}", method = RequestMethod.GET)
	public ModelAndView getProjectTasks(@PathVariable("project") String project, HttpSession session) {
		Long projectId = Long.valueOf(project);
		ProjectDTO projectDTO = projectFacade.getProject(projectId);

		ModelAndView modelAndView = new ModelAndView(WebConstants.TASKS_HTML_PATH);

		List<TaskDTO> projectTasks = taskACL.retrieveProjectTasksBasedOnACL(projectDTO);

		modelAndView.getModel().put("projectTasks", projectTasks);
		modelAndView.getModel().put("taskStatuses", TaskStatus.values());

		List<UserDTO> assignees = userFacade.getAllUsers().parallelStream()
				.filter(userDTO -> userDTO.getRole().equals(UserRole.ROLE_DEV) || userDTO.getRole()
						.equals(UserRole.ROLE_PROJECT_MANAGER)).collect(Collectors.toList());
		modelAndView.getModel().put("assignees", assignees);

		session.setAttribute("project", projectDTO);

		return modelAndView;
	}
}
