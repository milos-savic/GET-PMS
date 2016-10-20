package by.get.pms.service.user;

import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
@Service
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserPrecondition userPrecondition;

    @Autowired
    private UserService userService;

    @Override
    public UserDTO getUser(Long userId) {
        return userService.getUser(userId);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    public boolean userExists(Long userId) {
        return userService.userExists(userId);
    }

    @Override
    public UserDTO createUser(UserDTO userParams) {
        return userService.createUser(userParams);
    }

    @Override
    public void updateUser(UserDTO userParams) throws ApplicationException {
        userPrecondition.checkUpdateUserPreconditions(userParams);
        userService.updateUser(userParams);
    }

    @Override
    public void removeUser(Long userId) throws ApplicationException {
        userPrecondition.checkRemoveUserPreconditions(userId);
        userService.removeUser(userId);
    }
}
