package by.get.pms.web.controller.task;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.model.User;
import by.get.pms.security.Application;
import by.get.pms.service.project.ProjectFacade;
import by.get.pms.service.task.TaskService;
import by.get.pms.web.controller.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TaskService taskService;

    @RequestMapping(value = WebConstants.TASKS_URL + "/{project}", method = RequestMethod.GET)
    public ModelAndView getProjectTasks(@PathVariable("project") String project) {
        Long projectId = Long.valueOf(project);
        ProjectDTO projectDTO = projectFacade.getProject(projectId);

        ModelAndView modelAndView = new ModelAndView("task/tasks");
        List<TaskDTO> projectTasks = retrieveProjectTasks(projectDTO, Application.getInstance().getUser());
        modelAndView.getModel().put("projectTasks", projectTasks);

        return modelAndView;
    }

    private List<TaskDTO> retrieveProjectTasks(ProjectDTO projectDTO, User user) {
        return null;
    }
}
