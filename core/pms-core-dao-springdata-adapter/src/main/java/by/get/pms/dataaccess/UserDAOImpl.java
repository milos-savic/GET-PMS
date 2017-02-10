package by.get.pms.dataaccess;

import by.get.pms.data.UserData;
import by.get.pms.model.User;
import by.get.pms.model.UserAccount;
import by.get.pms.springdata.UserAccountRepository;
import by.get.pms.springdata.UserRepository;
import by.get.pms.utility.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
class UserDAOImpl implements UserDAO {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean userExistsByUserName(String username) {
		return userRepository.userExistsByUserName(username) > 0;
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
}
