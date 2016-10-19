package by.get.pms.service.user;

import by.get.pms.dto.UserDTO;

import java.util.List;

/**
 * Created by Milos.Savic on 10/18/2016.
 */

// only allowed to admin
public interface UserService {

	List<UserDTO> getAllUsers(Long userId);

	UserDTO createUser(UserDTO userParams);

	void updateUser(UserDTO userDTO);

	void removeUser(Long userId);
}
