package by.get.pms.security;

import by.get.pms.dto.UserDTO;
import by.get.pms.model.UserRole;
import by.get.pms.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by milos on 20-Nov-16.
 */
@Service
public class AccountConnectionSignUpService implements ConnectionSignUp {

    private static final Logger LOG = LoggerFactory.getLogger(AccountConnectionSignUpService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UsersDao usersDao;

    @Override
    public String execute(Connection<?> connection) {
        org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
        Random r = new Random();
        Long userId = r.nextLong();
        LOG.debug("Created user-id: " + userId);
        usersDao.createUser("" + userId, new by.get.pms.security.UserProfile("" + userId, profile));

        UserDTO userParams = new UserDTO(userId, profile.getFirstName(), profile.getLastName(), profile.getEmail(), profile.getUsername(),
                LocalDateTime.now(), true, UserRole.ROLE_GUEST);
        userService.createUser(userParams);

        return "" + userId;
    }
}
