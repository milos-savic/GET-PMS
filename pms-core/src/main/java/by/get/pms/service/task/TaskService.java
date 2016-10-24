package by.get.pms.service.task;

import by.get.pms.dto.*;

import java.util.List;

/**
 * Created by milos on 19-Oct-16.
 */
public interface TaskService {

	boolean taskExists(Long id);

	TaskDTO getTaskByName(String name);

	List<TaskDTO> getProjectTasksAvailableForAdmin(ProjectDTO projectDTO);

	List<TaskDTO> getProjectTasksAvailableForPM(ProjectDTO projectDTO, UserDTO projectManager);

	List<TaskDTO> getProjectTasksAvailableForDeveloper(ProjectDTO projectDTO, UserDTO developer);

	List<TaskDTO> getTasksAssignedToUser(UserDTO userDTO);

	TaskDTO createTask(TaskDTO taskDTO);

	void updateTask(TaskDTO taskDTO);

	void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM);

	void updateTaskByDeveloper(TaskUpdateParamsForDev taskUpdateParamsForDev);

	void removeTask(Long taskId);
}
