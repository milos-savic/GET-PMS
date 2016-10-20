package by.get.pms.service.user;

import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;

import java.util.List;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
public interface UserFacade {

    UserDTO getUser(Long userId);

    List<UserDTO> getAllUsers();

    boolean userExists(Long userId);

    UserDTO createUser(UserDTO userParams);

    void updateUser(UserDTO userParams) throws ApplicationException;

    void removeUser(Long userId) throws ApplicationException;
}
