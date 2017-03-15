package by.get.pms.springdata;

import by.get.pms.model.Project;
import by.get.pms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {
	Project findProjectByCode(String projectCode);

	List<Project> findProjectsByProjectManager(User projectManager);

	@Query("select count(p) from Project p where p.code = :projectCode")
	Integer projectExistsByCode(@Param("projectCode") String projectCode);
}
