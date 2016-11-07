package by.get.pms.web.controller.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.model.UserRole;
import by.get.pms.security.Application;
import by.get.pms.service.project.ProjectFacade;
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
 * Created by Milos.Savic on 10/26/2016.
 */
@RestController
@RequestMapping(value = WebConstants.REST_API_URL)
public class ProjectRestController {

	@Autowired
	private ResponseBuilderFactoryBean responseBuilder;

	@Autowired
	private ProjectFacade projectFacade;

	@RequestMapping(value = WebConstants.CREATE_PROJECT_URL, method = RequestMethod.POST)
	public Response createProject(@Validated ProjectDTO projectParams, BindingResult errors) {
		ResponseBuilder builder = responseBuilder.instance();
		if (errors.hasErrors()) {
			return builder.addErrors(errors).build();
		}

		UserRole userRole = Application.getInstance().getCurrentRole();

		switch (userRole) {
		case ADMIN:
			return createProjectByAdmin(projectParams, builder);
		case PROJECT_MANAGER:
			return createProjectByPM(projectParams, Application.getInstance().getUser(), builder);
		default:
			throw new RuntimeException(String.format("Not supported role: %s for project update!", userRole.name()));
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN_USER')")
	private Response createProjectByAdmin(ProjectDTO projectParams, ResponseBuilder builder) {
		try {
			ProjectDTO projectDtoNew = projectFacade.createProject(projectParams);
			return builder.indicateSuccess()
					.addSuccessMessage("projects.createProject.successfully.added", projectDtoNew.getId())
					.addObject("project", projectDtoNew).build();
		} catch (ApplicationException ae) {
			return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
		}
	}

	@PreAuthorize("hasRole('ROLE_PROJECT_MANAGER_USER')")
	private Response createProjectByPM(ProjectDTO projectParams, UserDTO projectManager, ResponseBuilder builder) {
		try {
			ProjectDTO projectDtoNew = projectFacade.createProjectByPM(projectManager, projectParams);
			return builder.indicateSuccess()
					.addSuccessMessage("projects.createProject.successfully.added", projectDtoNew.getId())
					.addObject("project", projectDtoNew).build();
		} catch (ApplicationException ae) {
			return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN_USER')")
	@RequestMapping(value = WebConstants.UPDATE_PROJECT_URL, method = RequestMethod.POST)
	private Response updateProject(ProjectDTO projectParams, BindingResult errors) {
		ResponseBuilder builder = responseBuilder.instance();
		if (errors.hasErrors()) {
			return builder.addErrors(errors).build();
		}

		try {
			projectFacade.updateProject(projectParams);
			return builder.indicateSuccess()
					.addSuccessMessage("projects.createProject.successfully.updated", projectParams.getName())
					.addObject("project", projectParams).build();
		} catch (ApplicationException ae) {
			return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
		}
	}

	@RequestMapping(value = WebConstants.DELETE_PROJECT_URL + "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN_USER')")
	public Response removeProject(@PathVariable("id") Long id) {
		final ResponseBuilder builder = responseBuilder.instance();

		try {
			projectFacade.removeProject(id);
			return builder.indicateSuccess().addSuccessMessage("tasks.removeTask.successfully.removed").build();
		} catch (ApplicationException e) {
			return builder.addErrorMessage(e.getMessage(), e.getParams()).build();
		}
	}

}
