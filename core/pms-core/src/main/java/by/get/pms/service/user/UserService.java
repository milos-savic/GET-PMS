package by.get.pms.service.user;

import by.get.pms.dtos.UserDTO;

import java.util.List;

/**
 * Created by Milos.Savic on 10/18/2016.
 */

public interface UserService {

	List<UserDTO> getAllUsers();

	UserDTO getUser(Long userId);

	UserDTO getUserByUserName(String username);

	boolean userExists(Long userId);

	UserDTO createUser(UserDTO userParams);

	void updateUser(UserDTO userParams);

	void removeUser(Long userId);

	boolean userExistsByUserName(String username);
}
