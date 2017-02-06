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
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

	@Autowired
	private MutableAclService mutableAclService;

	@RequestMapping(value = WebConstants.CREATE_PROJECT_URL, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_PROJECT_MANAGER')")
	public Response createProject(@Validated ProjectDTO projectParams, BindingResult errors) {
		ResponseBuilder builder = responseBuilder.instance();
		if (errors.hasErrors()) {
			return builder.addErrors(errors).build();
		}

		UserRole userRole = Application.getInstance().getCurrentRole();

		switch (userRole) {
		case ROLE_ADMIN:
			return createProjectByAdmin(projectParams, builder);
		case ROLE_PROJECT_MANAGER:
			return createProjectByPM(projectParams, Application.getInstance().getUser(), builder);
		default:
			throw new RuntimeException(String.format("Not supported role: %s for project create!", userRole.name()));
		}
	}

	private Response createProjectByAdmin(ProjectDTO projectParams, ResponseBuilder builder) {
		try {
			ProjectDTO projectDtoNew = projectFacade.createProject(projectParams);

			grantAdminAndPMAdministrativePermission(projectDtoNew);

			return builder.indicateSuccess()
					.addSuccessMessage("projects.createProject.successfully.added", projectDtoNew.getId())
					.addObject("project", projectDtoNew).build();
		} catch (ApplicationException ae) {
			return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
		}
	}

	private void grantAdminAndPMAdministrativePermission(ProjectDTO projectDtoNew) {
		addPermission(projectDtoNew, new GrantedAuthoritySid(UserRole.ROLE_ADMIN.name()),
				BasePermission.ADMINISTRATION);
		addPermission(projectDtoNew, new PrincipalSid(projectDtoNew.getProjectManager().getUserName()),
				BasePermission.ADMINISTRATION);
	}

	private void addPermission(ProjectDTO project, Sid recipient, Permission permission) {
		MutableAcl acl;
		ObjectIdentity oid = new ObjectIdentityImpl(ProjectDTO.class, project.getId());

		try {
			acl = (MutableAcl) mutableAclService.readAclById(oid);
		} catch (NotFoundException nfe) {
			acl = mutableAclService.createAcl(oid);
		}

		acl.insertAce(acl.getEntries().size(), permission, recipient, true);
		mutableAclService.updateAcl(acl);
	}

	private Response createProjectByPM(ProjectDTO projectParams, UserDTO projectManager, ResponseBuilder builder) {
		try {
			ProjectDTO projectDtoNew = projectFacade.createProjectByPM(projectManager, projectParams);

			grantAdminAndPMAdministrativePermission(projectDtoNew);

			return builder.indicateSuccess()
					.addSuccessMessage("projects.createProject.successfully.added", projectDtoNew.getId())
					.addObject("project", projectDtoNew).build();
		} catch (ApplicationException ae) {
			return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = WebConstants.UPDATE_PROJECT_URL, method = RequestMethod.POST)
	public Response updateProject(ProjectDTO projectParams, BindingResult errors) {
		ResponseBuilder builder = responseBuilder.instance();
		if (errors.hasErrors()) {
			return builder.addErrors(errors).build();
		}

		try {
			ProjectDTO projectFromDb = projectFacade.getProject(projectParams.getId());

			projectFacade.updateProject(projectParams);

			deletePermission(projectFromDb, new PrincipalSid(projectFromDb.getProjectManager().getUserName()),
					BasePermission.ADMINISTRATION);
			addPermission(projectParams, new PrincipalSid(projectParams.getProjectManager().getUserName()),
					BasePermission.ADMINISTRATION);

			return builder.indicateSuccess()
					.addSuccessMessage("projects.createProject.successfully.updated", projectParams.getName())
					.addObject("project", projectParams).build();
		} catch (ApplicationException ae) {
			return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
		}
	}

	private void deletePermission(ProjectDTO projectDTO, Sid recipient, Permission permission) {
		ObjectIdentity oid = new ObjectIdentityImpl(ProjectDTO.class, projectDTO.getId());
		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

		List<AccessControlEntry> entries = acl.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission)) {
				acl.deleteAce(i);
			}
		}

		mutableAclService.updateAcl(acl);
	}

	@RequestMapping(value = WebConstants.DELETE_PROJECT_URL + "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response removeProject(@PathVariable("id") Long id) {
		final ResponseBuilder builder = responseBuilder.instance();

		try {
			projectFacade.removeProject(id);

			// Delete the ACL information as well
			ObjectIdentity oid = new ObjectIdentityImpl(ProjectDTO.class, id);
			mutableAclService.deleteAcl(oid, false);

			return builder.indicateSuccess().addSuccessMessage("tasks.removeTask.successfully.removed").build();
		} catch (ApplicationException e) {
			return builder.addErrorMessage(e.getMessage(), e.getParams()).build();
		}
	}

}
