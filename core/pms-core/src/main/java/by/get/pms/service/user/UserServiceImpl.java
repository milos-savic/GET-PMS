package by.get.pms.service.user;

import by.get.pms.dataaccess.UserAccountRepository;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dtos.UserDTO;
import by.get.pms.model.User;
import by.get.pms.model.UserAccount;
import by.get.pms.utility.Dto2DataTransformers;
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
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public boolean userExists(Long userId) {
        return userRepository.exists(userId);
    }

    @Override
    public boolean userExistsByUserName(String username){
        return userRepository.userExistsByUserName(username) > 0;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = Lists.newArrayList(userRepository.findAll());
        return users.parallelStream().map(Dto2DataTransformers.USER_ENTITY_2_USER_DTO_TRANSFORMER::apply)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long userId) {
        User user = userRepository.findOne(userId);
        return Dto2DataTransformers.USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(user);
    }

    @Override
    public UserDTO getUserByUserName(String username) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username);

        if (userAccount == null) return null;

        User user = userAccount.getUser();
        return Dto2DataTransformers.USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(user);
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
        newUserAccount.setRole(userParams.getRole());
        newUserAccount.setCreationDateTime(LocalDateTime.now());
        newUserAccount = userAccountRepository.save(newUserAccount);

        newUser.setUserAccount(newUserAccount);

        return Dto2DataTransformers.USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(newUser);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userParams) {
        User userFromDb = userRepository.findOne(userParams.getId());
        BeanUtils.copyProperties(userParams, userFromDb);

        UserAccount userAccountFromDb = userAccountRepository.findUserAccountByUser(userFromDb);
        BeanUtils.copyProperties(userParams, userAccountFromDb, "id", "creationDate");

        userAccountFromDb.setRole(userParams.getRole());
    }

    @Override
    @Transactional
    public void removeUser(Long userId) {
        User userFromDb = userRepository.findOne(userId);
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(userFromDb);
        userAccountRepository.delete(userAccount);

        userRepository.delete(userId);
    }
}
