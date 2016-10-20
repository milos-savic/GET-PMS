package by.get.pms.service.user;

import by.get.pms.dto.UserDTO;
import by.get.pms.model.User;

import java.util.List;

/**
 * Created by Milos.Savic on 10/18/2016.
 */

// only allowed to admin
public interface UserService {

	List<UserDTO> getAllUsers();

    UserDTO getUser(Long userId);

    boolean userExists(Long userId);

	UserDTO createUser(UserDTO userParams);

	void updateUser(UserDTO userParams);

	void removeUser(Long userId);
}
