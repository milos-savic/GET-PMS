package by.get.pms.web.controller.task;

import by.get.pms.dto.*;
import by.get.pms.model.TaskStatus;
import by.get.pms.model.UserRole;
import by.get.pms.service.entitlement.EntitlementService;
import by.get.pms.service.project.ProjectFacade;
import by.get.pms.service.task.TaskFacade;
import by.get.pms.service.user.UserFacade;
import by.get.pms.service.user.UserService;
import by.get.pms.web.controller.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
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
    private EntitlementService entitlementService;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserService userService;

    @RequestMapping(value = WebConstants.TASKS_URL + "/{project}", method = RequestMethod.GET)
    public ModelAndView getProjectTasks(@PathVariable("project") String project, HttpSession session) {
        Long projectId = Long.valueOf(project);
        ProjectDTO projectDTO = projectFacade.getProject(projectId);

        ModelAndView modelAndView = new ModelAndView(WebConstants.TASKS_HTML_PATH);

        //UserDTO loggedInUser = Application.getInstance().getUser();
        UserDTO loggedInUser = userService.getUser(10L);


        List<TaskDTO> projectTasks = retrieveProjectTasks(projectDTO, loggedInUser);
        modelAndView.getModel().put("projectTasks", projectTasks);
        modelAndView.getModel().put("taskStatuses", TaskStatus.values());

        List<UserDTO> assignees = userFacade.getAllUsers().parallelStream()
                .filter(userDTO -> userDTO.getRole().equals(UserRole.DEV) || userDTO.getRole()
                        .equals(UserRole.PROJECT_MANAGER)).collect(Collectors.toList());
        modelAndView.getModel().put("assignees", assignees);

        session.setAttribute("project", projectDTO);

        return modelAndView;
    }

    private List<TaskDTO> retrieveProjectTasks(ProjectDTO project, UserDTO user) {
        List<EntitlementDTO> entitlementsForTasksPermittedToUser = entitlementService
                .getEntitlementsForObjectTypePermittedToUser(user.getUserName(), ObjectType.TASK);
        Set<Long> taskIds = entitlementsForTasksPermittedToUser.parallelStream().map(EntitlementDTO::getObjectid)
                .collect(Collectors.toSet());
        List<TaskDTO> tasks = taskFacade.getTasksByIds(taskIds);

        return tasks.parallelStream().filter(taskDTO -> project.equals(taskDTO.getProject()))
                .collect(Collectors.toList());
    }
}
