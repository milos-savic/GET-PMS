package by.get.pms.dataaccess;

import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task findTaskByName(@Param("name") String name);

    @Query("select t from Task t where t.assignee = :assignee")
    List<Task> findTasksAssignedToUser(@Param("assignee") User assignee);

    @Query("select t from Task t where t.project = :project")
    List<Task> findProjectTasks(@Param("project") Project project);
}
