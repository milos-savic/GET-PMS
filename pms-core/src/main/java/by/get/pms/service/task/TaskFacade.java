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

	// allowed to admin and pm
	TaskDTO createTask(TaskDTO taskParams) throws ApplicationException;

	// allowed to admin
	void updateTask(TaskDTO taskParams) throws ApplicationException;

	// allowed to pm
	void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM);

	// allowed to dev
	void updateTaskByDeveloper(TaskUpdateParamsForDev taskUpdateParamsForDev);

	void removeTask(Long taskId) throws ApplicationException;
}
