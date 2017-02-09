package by.get.pms.facade.user;

import by.get.pms.dtos.UserDTO;
import by.get.pms.exception.ApplicationException;

import java.util.List;

/**
 * Created by Milos.Savic on 2/9/2017.
 */
public interface UserFacade {

    boolean userExistsByUserName(String username);

    List<UserDTO> getAllUsers();

    UserDTO getUserByUserName(String username);

    UserDTO createUser(UserDTO userParams) throws ApplicationException;

    void updateUser(UserDTO userParams) throws ApplicationException;

    void removeUser(Long userId) throws ApplicationException;
}
