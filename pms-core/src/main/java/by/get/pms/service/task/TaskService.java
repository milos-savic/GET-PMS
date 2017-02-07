package by.get.pms.service.task;

import by.get.pms.dto.*;

import java.util.List;
import java.util.Set;

/**
 * Created by milos on 19-Oct-16.
 */
public interface TaskService {

    List<TaskDTO> getAll();

    boolean taskExists(Long id);

    boolean taskExistsByProjectAndName(ProjectDTO project, String name);

    TaskDTO getTaskByProjectAndName(ProjectDTO projectDTO, String name);

    List<TaskDTO> getTasksByIds(Set<Long> taskIds);

    List<TaskDTO> getProjectTasks(ProjectDTO projectDTO);

    List<TaskDTO> getTasksAssignedToUser(UserDTO userDTO);

    TaskDTO createTask(TaskDTO taskDTO);

    void updateTask(TaskDTO taskDTO);

    void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM);

    void updateTaskByDeveloper(TaskUpdateParamsForDev taskUpdateParamsForDev);

    void removeTask(Long taskId);
}
