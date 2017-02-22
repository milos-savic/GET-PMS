package by.get.pms.dataaccess;

import by.get.pms.data.UserData;
import by.get.pms.model.User;
import by.get.pms.model.UserAccount;
import by.get.pms.model.UserRole;
import by.get.pms.springdata.UserAccountRepository;
import by.get.pms.springdata.UserRepository;
import by.get.pms.utility.Transformers;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
@Repository
class UserDAOImpl implements UserDAO {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean exists(Long userId) {
        return userRepository.exists(userId);
    }

    @Override
    public boolean userExistsByUserName(String username) {
        return userRepository.userExistsByUserName(username) > 0;
    }

    @Override
    public List<UserData> findAll() {
        List<User> users = Lists.newArrayList(userRepository.findAll());

        return users.parallelStream().map(Transformers.USER_ENTITY_2_USER_DATA_TRANSFORMER::apply)
                .collect(Collectors.toList());
    }

    @Override
    public UserData findOne(Long userId) {
        User user = userRepository.findOne(userId);
        return Transformers.USER_ENTITY_2_USER_DATA_TRANSFORMER.apply(user);
    }

    @Override
    public UserData getUserByUserName(String username) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username);

        if (userAccount == null)
            return null;

        User user = userAccount.getUser();
        return Transformers.USER_ENTITY_2_USER_DATA_TRANSFORMER.apply(user);
    }

    @Override
    public UserData create(UserData userParams) {
        User newUser = new User();
        BeanUtils.copyProperties(userParams, newUser);
        newUser = userRepository.save(newUser);

        UserAccount newUserAccount = new UserAccount();
        BeanUtils.copyProperties(userParams, newUserAccount, "creationDate");
        newUserAccount.setUser(newUser);
        newUserAccount.setRole(UserRole.make(userParams.getRole().name()));
        newUserAccount.setCreationDateTime(LocalDateTime.now());
        newUserAccount = userAccountRepository.save(newUserAccount);

        newUser.setUserAccount(newUserAccount);

        return Transformers.USER_ENTITY_2_USER_DATA_TRANSFORMER.apply(newUser);
    }

    @Override
    public void update(UserData userParams) {
        User userFromDb = userRepository.findOne(userParams.getId());
        BeanUtils.copyProperties(userParams, userFromDb);

        UserAccount userAccountFromDb = userAccountRepository.findUserAccountByUser(userFromDb);
        BeanUtils.copyProperties(userParams, userAccountFromDb, "id", "creationDate");
        userAccountFromDb.setRole(UserRole.make(userParams.getRole().name()));
    }

    @Override
    public void delete(Long userId) {
        User userFromDb = userRepository.findOne(userId);
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(userFromDb);
        userAccountRepository.delete(userAccount);

        userRepository.delete(userId);
    }
}
