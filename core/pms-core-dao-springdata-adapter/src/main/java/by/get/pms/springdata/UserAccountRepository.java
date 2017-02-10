package by.get.pms.springdata;

import by.get.pms.model.User;
import by.get.pms.model.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

	UserAccount findUserAccountByUser(@Param("user") User user);

	@Query("select ua from UserAccount ua where UPPER(ua.userName) = UPPER(:userName)")
	UserAccount findUserAccountByUsername(@Param("userName") String userName);
}
