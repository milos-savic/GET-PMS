package by.get.pms.dataaccess;

import by.get.pms.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public interface ProjectRepository extends CrudRepository<Project, Long>{

	@Query("select p from Project p where p.code = :projectCode")
	public Project findProjectByCode(@Param("projectCode") String projectCode);

	@Query("select p from Project p where p = :projectManager")
	public List<Project> findProjectManagerProjects(@Param("projectManager") String projectManager);
}
