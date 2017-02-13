package by.get.pms.service.task;

import by.get.pms.dtos.*;

import java.util.List;

/**
 * Created by milos on 19-Oct-16.
 */
public interface TaskService {

    List<TaskDTO> getAll();

    boolean taskExists(Long id);

    boolean taskExistsByProjectAndName(ProjectDTO project, String name);

    TaskDTO getTask(Long taskId);

    TaskDTO getTaskByProjectAndName(ProjectDTO projectDTO, String name);

    List<TaskDTO> getProjectTasks(ProjectDTO projectDTO);

    List<TaskDTO> getTasksAssignedToUser(UserDTO userDTO);

    TaskDTO createTask(TaskDTO taskParams);

    void updateTask(TaskDTO taskDTO);

    void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM);

    void updateTaskByDeveloper(TaskUpdateParamsForDev taskUpdateParamsForDev);

    void removeTask(Long taskId);
}
