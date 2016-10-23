package by.get.pms.service.task;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.dataaccess.TaskRepository;
import by.get.pms.dto.*;
import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.utility.Transformers;
import by.get.pms.validation.Admin;
import by.get.pms.validation.Developer;
import by.get.pms.validation.ProjectManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by milos on 23-Oct-16.
 */
@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<TaskDTO> getProjectTasksAvailableForAdmin(ProjectDTO projectDTO, @Admin UserDTO admin) {
        return getProjectTasks(projectDTO);
    }

    public List<TaskDTO> getProjectTasksAvailableForPM(ProjectDTO projectDTO, @ProjectManager UserDTO projectManager) {
        List<TaskDTO> projectTasks = getProjectTasks(projectDTO);

        if (projectDTO.getProjectManager().equals(projectManager)) return projectTasks;

        return filterTasksAvailableForUser(projectTasks, projectManager);
    }

    public List<TaskDTO> getProjectTasksAvailableForDeveloper(ProjectDTO projectDTO, @Developer UserDTO developer) {
        List<TaskDTO> projectTasks = getProjectTasks(projectDTO);

        return filterTasksAvailableForUser(projectTasks, developer);
    }

    private List<TaskDTO> filterTasksAvailableForUser(List<TaskDTO> tasks, UserDTO user) {
        return tasks.parallelStream()
                .filter(taskDTO -> (taskDTO.getAssigneeDTO() == null) || user.equals(taskDTO.getAssigneeDTO()))
                .collect(Collectors.toList());
    }

    private List<TaskDTO> getProjectTasks(ProjectDTO projectDTO) {
        Project project = projectRepository.findOne(projectDTO.getId());
        List<Task> projectTasks = taskRepository.findProjectTasks(project);

        return projectTasks.parallelStream()
                .map(task -> Transformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(task))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task newTask = new Task();
        BeanUtils.copyProperties(taskDTO, newTask);
        newTask = taskRepository.save(newTask);

        return Transformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(newTask);
    }

    @Override
    @Transactional
    public void updateTask(TaskDTO taskDTO) {
        Task taskFromDb = taskRepository.findOne(taskDTO.getId());
        BeanUtils.copyProperties(taskDTO, taskFromDb);
    }

    @Override
    @Transactional
    public void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM) {
        Task taskFromDb = taskRepository.findOne(taskUpdateParamsForPM.getId());
        BeanUtils.copyProperties(taskUpdateParamsForPM, taskFromDb);
    }

    @Override
    @Transactional
    public void updateTaskByDeveloper(TaskUpdateParamsForDev taskUpdateParamsForDev) {
        Task taskFromDb = taskRepository.findOne(taskUpdateParamsForDev.getId());
        BeanUtils.copyProperties(taskUpdateParamsForDev, taskFromDb);
    }

    @Override
    @Transactional
    public void removeTask(TaskDTO taskDTO) {
        taskRepository.delete(taskDTO.getId());
    }
}
