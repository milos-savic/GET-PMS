package by.get.pms.dataaccess;

import by.get.pms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select count(*) from UserAccount ua where ua.userName = :userName")
    Integer userExistsByUserName(@Param("userName") String userName);
}
