package by.get.pms.service.task;

import by.get.pms.dto.*;
import by.get.pms.exception.ApplicationException;

import java.util.List;
import java.util.Set;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
public interface TaskFacade {

	List<TaskDTO> getAll();

	TaskDTO createTask(TaskDTO taskParams) throws ApplicationException;

	void updateTask(TaskDTO taskParams) throws ApplicationException;

	void updateTaskByProjectManager(TaskDTO taskParams) throws ApplicationException;

	void updateTaskByDeveloper(TaskDTO taskParams) throws ApplicationException;

	void removeTask(Long taskId) throws ApplicationException;

	List<TaskDTO> getTasksByIds(Set<Long> taskIds);

}
