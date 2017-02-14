package by.get.pms.service.user;

import by.get.pms.data.UserData;
import by.get.pms.dataaccess.UserDAO;
import by.get.pms.dtos.UserDTO;
import by.get.pms.utility.Data2DtoTransformers;
import by.get.pms.utility.Dto2DataTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
@Service
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@Override
	public boolean userExists(Long userId) {
		return userDao.exists(userId);
	}

	@Override
	public boolean userExistsByUserName(String username) {
		return userDao.userExistsByUserName(username);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserData> users = userDao.findAll();
		return users.parallelStream().map(Data2DtoTransformers.USER_DATA_2_USER_DTO_TRANSFORMER::apply)
				.collect(Collectors.toList());
	}

	@Override
	public UserDTO getUser(Long userId) {
		UserData user = userDao.findOne(userId);
		return Data2DtoTransformers.USER_DATA_2_USER_DTO_TRANSFORMER.apply(user);
	}

	@Override
	public UserDTO getUserByUserName(String username) {
		UserData userData = userDao.getUserByUserName(username);
		return userData == null ? null : Data2DtoTransformers.USER_DATA_2_USER_DTO_TRANSFORMER.apply(userData);
	}

	@Override
	@Transactional
	public UserDTO createUser(UserDTO userParams) {
		UserData userData = userDao.create(Dto2DataTransformers.USER_DTO_2_USER_DATA_TRANSFORMER.apply(userParams));
		return Data2DtoTransformers.USER_DATA_2_USER_DTO_TRANSFORMER.apply(userData);
	}

	@Override
	@Transactional
	public void updateUser(UserDTO userParams) {
		userDao.update(Dto2DataTransformers.USER_DTO_2_USER_DATA_TRANSFORMER.apply(userParams));
	}

	@Override
	@Transactional
	public void removeUser(Long userId) {
		userDao.delete(userId);
	}
}
