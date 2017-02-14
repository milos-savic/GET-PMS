package by.get.pms.dataaccess;

import by.get.pms.data.UserData;

import java.util.List;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface UserDAO {

	boolean exists(Long userId);

	boolean userExistsByUserName(String userName);

	List<UserData> findAll();

	UserData findOne(Long userId);

	UserData getUserByUserName(String username);

	UserData create(UserData userParams);

	void update(UserData userParams);

	void delete(Long userId);
}
