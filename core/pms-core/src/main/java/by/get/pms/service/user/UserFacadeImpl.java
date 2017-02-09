package by.get.pms.service.user;

import by.get.pms.dtos.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.facade.user.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
@Service
class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserPreconditions userPreconditions;

	@Autowired
	private UserService userService;

	@Override
	public boolean userExistsByUserName(String username) {
		return userService.userExistsByUserName(username);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}

	@Override
	public UserDTO getUserByUserName(String username) {
		return userService.getUserByUserName(username);
	}

	@Override
	public UserDTO createUser(UserDTO userParams) throws ApplicationException {
		userPreconditions.checkCreateUserPreconditions(userParams);
		return userService.createUser(userParams);
	}

	@Override
	public void updateUser(UserDTO userParams) throws ApplicationException {
		userPreconditions.checkUpdateUserPreconditions(userParams);
		userService.updateUser(userParams);
	}

	@Override
	public void removeUser(Long userId) throws ApplicationException {
		userPreconditions.checkRemoveUserPreconditions(userId);
		userService.removeUser(userId);
	}
}
