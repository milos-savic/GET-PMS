package by.get.pms.acl;

import by.get.pms.dto.TaskDTO;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

/**
 * Created by Milos.Savic on 2/7/2017.
 */
public interface TaskACL {

	@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'administration')")
	List<TaskDTO> retrieveTasksBasedOnACL();

	void createACL(TaskDTO task);

	void updateACL(TaskDTO oldTask, TaskDTO newTask);

	void deleteACL(Long taskId);
}
