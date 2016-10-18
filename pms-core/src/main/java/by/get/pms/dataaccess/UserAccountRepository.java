package by.get.pms.dataaccess;

import by.get.pms.model.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by milos on 12-Oct-16.
 */
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    @Query("select ua from UserAccount ua where ua.user.id = :userId")
    UserAccount findUserAccountByUser(@Param("userId") Long userId);

    @Query("select ua from UserAccount ua where UPPER(ua.username) = UPPER(:username)")
    UserAccount findUserAccountByUsername(@Param("username") String username);

    @Query("select ua from UserAccount ua where UPPER(ua.username) = UPPER(:username) and ua.active='Y'")
    UserAccount findActiveUserAccountByUsername(@Param("username") String username);
}
