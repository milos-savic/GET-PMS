package by.get.pms.springdata;

import by.get.pms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {
	@Query("select case when exists (select ua from UserAccount ua where ua.userName = :userName)"
			+ " then 1 else 0 end")
	Integer userExistsByUserName(@Param("userName") String userName);
}
