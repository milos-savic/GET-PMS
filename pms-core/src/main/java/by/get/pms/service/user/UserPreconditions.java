package by.get.pms.service.user;

import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
interface UserPreconditions {

	void checkCreateUserPreconditions(UserDTO userParams) throws ApplicationException;

	void checkUpdateUserPreconditions(UserDTO userParams) throws ApplicationException;

	void checkRemoveUserPreconditions(Long userId) throws ApplicationException;
}
