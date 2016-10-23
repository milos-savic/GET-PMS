package by.get.pms.service.task;

import by.get.pms.dto.*;
import by.get.pms.validation.Admin;
import by.get.pms.validation.Developer;
import by.get.pms.validation.ProjectManager;

import java.util.List;

/**
 * Created by milos on 19-Oct-16.
 */
public interface TaskService {

    List<TaskDTO> getProjectTasksAvailableForAdmin(ProjectDTO projectDTO, @Admin UserDTO admin);

    List<TaskDTO> getProjectTasksAvailableForPM(ProjectDTO projectDTO, @ProjectManager UserDTO projectManager);

    List<TaskDTO> getProjectTasksAvailableForDeveloper(ProjectDTO projectDTO, @Developer UserDTO developer);

    // allowed to admin and pm
    TaskDTO createTask(TaskDTO taskDTO);

    // allowed to admin
    void updateTask(TaskDTO taskDTO);

    // allowed to pm
    void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM);

    // allowed to dev
    void updateTaskByDeveloper(TaskUpdateParamsForDev taskUpdateParamsForDev);

    void removeTask(TaskDTO taskDTO);
}
