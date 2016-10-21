package by.get.pms.service.task;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.model.Task;

import java.util.List;

/**
 * Created by milos on 19-Oct-16.
 */
public interface TaskService {

    // for admin and pm for that projectDTO
    List<TaskDTO> getProjectTasks(ProjectDTO projectDTO);

    // for dev
    List<TaskDTO> getTasksAvailableForDeveloper(UserDTO developer);
    List<TaskDTO> getProjectTasksAvailableForDeveloper(ProjectDTO projectDTO, UserDTO developer);

    // allowed to admin and pm
    TaskDTO createTask(TaskDTO taskDTO);

    // allowed to admin
    void updateTask(TaskDTO taskDTO);

    // allowed to pm
    void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM);

    // allowed to dev
    void updateTaskByDeveloper(TaskUpdateParamsForDev taskUpdateParamsForDev);

    void deleteTask(TaskDTO taskDTO);
}
