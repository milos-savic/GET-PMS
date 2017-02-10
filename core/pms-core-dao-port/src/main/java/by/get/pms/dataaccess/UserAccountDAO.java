package by.get.pms.dataaccess;

import by.get.pms.data.UserAccountData;
import by.get.pms.data.UserData;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface UserAccountDAO {

	UserAccountData findUserAccountByUser(UserData user);

	UserAccountData findUserAccountByUsername(String userName);
}
