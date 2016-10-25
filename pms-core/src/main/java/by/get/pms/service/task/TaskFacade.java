package by.get.pms.service.task;

import by.get.pms.dto.*;
import by.get.pms.exception.ApplicationException;

import java.util.List;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
public interface TaskFacade {

	List<TaskDTO> getProjectTasksAvailableForAdmin(ProjectDTO projectDTO, UserDTO admin);

	List<TaskDTO> getProjectTasksAvailableForPM(ProjectDTO projectDTO, UserDTO projectManager);

	List<TaskDTO> getProjectTasksAvailableForDeveloper(ProjectDTO projectDTO, UserDTO developer);

	TaskDTO createTask(TaskDTO taskParams) throws ApplicationException;

	void updateTask(TaskDTO taskParams) throws ApplicationException;

	void updateTaskByProjectManager(TaskDTO taskParams) throws ApplicationException;

	void updateTaskByDeveloper(TaskDTO taskParams) throws ApplicationException;

	void removeTask(Long taskId) throws ApplicationException;
}
