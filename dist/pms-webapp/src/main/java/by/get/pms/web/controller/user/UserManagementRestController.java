package by.get.pms.web.controller.user;

import by.get.pms.dtos.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.facade.user.UserFacade;
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
 * Created by milos on 20-Oct-16.
 */
@RestController
@RequestMapping(value = WebConstants.REST_API_URL)
public class UserManagementRestController {

	@Autowired
	private ResponseBuilderFactoryBean responseBuilder;

	@Autowired
	private UserFacade userFacade;

	@RequestMapping(value = WebConstants.CREATE_UPDATE_USER_URL, method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response createUpdateUser(@Validated UserDTO userParams, BindingResult errors) {
		ResponseBuilder builder = responseBuilder.instance();
		if (errors.hasErrors()) {
			return builder.addErrors(errors).build();
		}

		Long userId = userParams.getId();
		UserDTO userDtoNew = userParams;
		String messageKey;
		try {
			if (userId != null) {
				userFacade.updateUser(userDtoNew);
				messageKey = "users.createUpdateUser.successfully.updated";
			} else {
				userDtoNew = userFacade.createUser(userParams);
				messageKey = "users.createUpdateUser.successfully.added";
			}
		} catch (ApplicationException ae) {
			return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
		}

		return builder.indicateSuccess().addSuccessMessage(messageKey, userDtoNew.getUserName()).addObject("user", userDtoNew)
				.build();
	}

	@RequestMapping(value = WebConstants.DELETE_USER_URL + "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response removeUser(@PathVariable("id") final Long id) {
		final ResponseBuilder builder = responseBuilder.instance();

		try {
			userFacade.removeUser(id);
			return builder.indicateSuccess().addSuccessMessage("users.removeUser.successfully.removed")
					.build();
		} catch (ApplicationException e) {
			return builder.addErrorMessage(e.getMessage(), e.getParams()).build();
		}
	}
}
