package by.get.pms.service.user;

import by.get.pms.dataaccess.UserAccountRepository;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dto.UserDTO;
import by.get.pms.model.User;
import by.get.pms.model.UserAccount;
import by.get.pms.model.UserRole;
import by.get.pms.utility.Transformers;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = Lists.newArrayList(userRepository.findAll());
        return users.parallelStream().map(user -> Transformers.USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long userId) {
        User user = userRepository.findOne(userId);
        return Transformers.USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(user);
    }

    public boolean userExists(Long userId){
        return userRepository.exists(userId);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userParams) {
        User newUser = new User();
        BeanUtils.copyProperties(userParams, newUser);
        newUser = userRepository.save(newUser);

        UserAccount newUserAccount = new UserAccount();
        BeanUtils.copyProperties(userParams, newUserAccount, "creationDate");
        newUserAccount.setUser(newUser);
        newUserAccount.setRole(UserRole.make(userParams.getRoleName()));
        newUserAccount.setCreationDateTime(LocalDateTime.now());
        newUserAccount = userAccountRepository.save(newUserAccount);

        newUser.setUserAccount(newUserAccount);

        return Transformers.USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(newUser);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userParams) {
        User userFromDb = userRepository.findOne(userParams.getId());
        BeanUtils.copyProperties(userParams, userFromDb);

        UserAccount userAccountFromDb = userAccountRepository.findUserAccountByUser(userParams.getId());
        BeanUtils.copyProperties(userParams, userAccountFromDb, "creationDate");

        userAccountFromDb.setRole(UserRole.make(userParams.getRoleName()));
    }

    @Override
    @Transactional
    public void removeUser(Long userId) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(userId);
        userAccountRepository.delete(userAccount);

        userRepository.delete(userId);
    }
}
