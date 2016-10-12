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

    @Query("select ua from UserAccount as ua where ua.user.id = :userId")
    UserAccount findUserAccountByUser(@Param("userId") Long userId);

    @Query(" select ua from UserAccount ua where ua.id in (:aids) order by ua.user.firstName")
    List<UserAccount> findUserAccountsByIds(@Param("aids") Set<Long> aids);

    @Query("select ua from UserAccount ua where UPPER(ua.username) like UPPER(:username) order by ua.username")
    List<UserAccount> findDuplicateUserAccountsByUserName(@Param("username") String username);

    @Query("select a from UserAccount a left join fetch a.user where UPPER(a.username) = UPPER(:username)")
    UserAccount findUserAccountByUsername(@Param("username") String username);

    @Query("select a from UserAccount a left join fetch a.user where UPPER(a.username) = UPPER(:username) and a.active='Y'")
    UserAccount findActiveUserAccountByUsername(@Param("username") String username);

}
