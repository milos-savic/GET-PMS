package by.get.pms.dataaccess;

import by.get.pms.model.Project;
import by.get.pms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {

	Project findProjectByCode(@Param("projectCode") String projectCode);

	List<Project> findProjectsByProjectManager(@Param("projectManager") User projectManager);

	@Query("select count(p) from Project p where p.code = :projectCode")
	Integer projectExistsByCode(@Param("projectCode") String projectCode);
}
