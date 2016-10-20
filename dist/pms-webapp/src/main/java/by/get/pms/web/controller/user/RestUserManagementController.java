package by.get.pms.web.controller.user;

import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.service.user.UserFacade;
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
public class RestUserManagementController {

    @Autowired
    private ResponseBuilderFactoryBean responseBuilder;

    @Autowired
    private UserFacade userFacade;


    @RequestMapping(value = WebConstants.CREATE_UPDATE_USER, method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
    public Response createUpdateUser(@Validated UserDTO userParams, BindingResult errors) {
        ResponseBuilder builder = responseBuilder.instance();

        Long userId = userParams.getId();
        if (userId != null && userId != -1L && !userFacade.userExists(userId)) { // additional check for edit
            return builder.addErrorMessage("users.createUpdateUser.NonExistingRecordForUpdate", userId).build();
        }

        if (errors.hasErrors()) {
            return builder.addErrors(errors).build();
        }

        UserDTO userDtoNew = userParams;

        try {
            if (userId != null) {
                userFacade.updateUser(userDtoNew);
            } else {
                userDtoNew = userFacade.createUser(userParams);
            }
        } catch (ApplicationException ae) {
            return builder.addErrorMessage(ae.getMessage(), ae.getParams()).build();
        }

        String messageKey = "users.createUpdateUser.successfully.added";
        if (userId != null) {
            messageKey = "users.createUpdateUser.successfully.updated";
        }

        return builder.indicateSuccess().addSuccessMessage(messageKey, userDtoNew.getId())
                .addObject("user", userDtoNew).build();
    }

    @RequestMapping(value = WebConstants.DELETE_USER + "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
    public Response deleteUser(@PathVariable("id") final Long id) {
        final ResponseBuilder builder = responseBuilder.instance();

        // Spring MVC doesn't support @Validated @ExistingRecord for @PathVariable
        if (id == null || !userFacade.userExists(id)) {
            return builder.addErrorMessage("users.createUpdateUser.NonExistingRecordForRemove", id).build();
        }

        try {
            userFacade.removeUser(id);
            return builder.indicateSuccess().addSuccessMessage("users.createUpdateUser.successfully.removed", id).build();
        } catch (ApplicationException e) {
            return builder.addErrorMessage(e.getMessage(), e.getParams()).build();
        }
    }
}
