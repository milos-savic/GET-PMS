package by.get.pms.springdata;

import by.get.pms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {
	@Query("select count(ua) from UserAccount ua where ua.userName = :userName")
	Integer userExistsByUserName(@Param("userName") String userName);
}
