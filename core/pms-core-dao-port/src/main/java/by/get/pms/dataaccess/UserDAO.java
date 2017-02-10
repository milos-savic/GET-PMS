package by.get.pms.dataaccess;

import by.get.pms.data.UserData;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface UserDAO {
	boolean userExistsByUserName(String userName);
	UserData getUserByUserName(String username);
}
