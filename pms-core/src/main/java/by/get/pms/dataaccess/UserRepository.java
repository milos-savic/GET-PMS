package by.get.pms.dataaccess;

import by.get.pms.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
