package by.get.pms.facade.task;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.TaskDTO;
import by.get.pms.exception.ApplicationException;

import java.util.List;

/**
 * Created by Milos.Savic on 2/9/2017.
 */
public interface TaskFacade {
    TaskDTO getTask(Long taskId);

    List<TaskDTO> getProjectTasks(ProjectDTO projectDTO);

    TaskDTO createTask(TaskDTO taskParams) throws ApplicationException;

    void updateTask(TaskDTO taskParams) throws ApplicationException;

    void updateTaskByProjectManager(TaskDTO taskParams) throws ApplicationException;

    void updateTaskByDeveloper(TaskDTO taskParams) throws ApplicationException;

    void removeTask(Long taskId) throws ApplicationException;

}
