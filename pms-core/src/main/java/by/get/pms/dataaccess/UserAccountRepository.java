package by.get.pms.dataaccess;

import by.get.pms.model.User;
import by.get.pms.model.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by milos on 12-Oct-16.
 */
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findUserAccountByUser(@Param("user") User user);

    @Query("select ua from UserAccount ua where UPPER(ua.userName) = UPPER(:userName)")
    UserAccount findUserAccountByUsername(@Param("userName") String userName);
}
