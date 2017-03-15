package by.get.pms.springdata;

import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

	Task findTaskByProjectAndName(Project project, String name);

	List<Task> findTasksByAssignee(User assignee);

	List<Task> findTasksByProject(Project project);

	@Query("select count(t) from Task t where t.project = :project and t.name = :name")
	Integer taskExistsByProjectAndName(@Param("project") Project project, @Param("name") String name);
}
