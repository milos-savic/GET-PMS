package by.get.pms.web.controller.task;

import by.get.pms.dto.TaskDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.service.task.TaskFacade;
import by.get.pms.web.controller.WebConstants;
import by.get.pms.web.response.Response;
import by.get.pms.web.response.ResponseBuilder;
import by.get.pms.web.response.ResponseBuilderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
@RestController
@RequestMapping(value = WebConstants.REST_API_URL)
public class TaskRestController {

	@Autowired
	private ResponseBuilderFactoryBean responseBuilder;

	@Autowired
	private TaskFacade taskFacade;

	@RequestMapping(value = WebConstants.CREATE_TASK_URL, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN_USER') or (hasRole('ROLE_PROJECT_MANAGER_USER') and #taskParams.getProjectDTO().getProjectManager().equals(T(by.get.pms.security.Application).getInstance().getUser()))")
	public Response createTask(@Validated TaskDTO taskParams, BindingResult errors) {
		ResponseBuilder builder = responseBuilder.instance();
		if (errors.hasErrors()) {
			return builder.addErrors(errors).build();
		}

		try {
			TaskDTO taskDtoNew = taskFacade.createTask(taskParams);
			return builder.indicateSuccess()
					.addSuccessMessage("tasks.createTask.successfully.added", taskDtoNew.getId())
					.addObject("task", taskDtoNew).build();
		} catch (ApplicationException ae) {
			return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
		}

	}
}
