package by.get.pms.service;

import by.get.pms.dto.UserDTO;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public interface UserService {

	UserDTO findUser(Long userId);

	UserDTO createUser(UserDTO userParams);

	void updateUser(UserDTO userDTO);

	void removeUser(Long userId);
}
