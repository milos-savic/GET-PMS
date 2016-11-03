package by.get.pms.service.task;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.dataaccess.TaskRepository;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dto.*;
import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.model.User;
import by.get.pms.utility.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean taskExists(Long taskId) {
        return taskRepository.exists(taskId);
    }

    @Override
    public boolean taskExistsByProjectAndName(ProjectDTO project, String name) {
        Project projectEntity = projectRepository.findOne(project.getId());
        return taskRepository.taskExistsByProjectAndName(projectEntity, name) > 0;
    }

    @Override
    public TaskDTO getTaskByProjectAndName(ProjectDTO projectDTO, String name) {
        Project project = projectRepository.findOne(projectDTO.getId());
        Task task = taskRepository.findTaskByProjectAndName(project, name);
        return Transformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(task);
    }

    @Override
    public List<TaskDTO> getProjectTasks(ProjectDTO projectDTO) {
        Project project = projectRepository.findOne(projectDTO.getId());
        List<Task> projectTasks = taskRepository.findTasksByProject(project);

        return projectTasks.parallelStream().map(task -> Transformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(task))
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByIds(Set<Long> taskIds) {
        List<Task> tasks = taskRepository.findTasksByIds(taskIds);
        return tasks.parallelStream().map(task -> Transformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(task))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksAssignedToUser(UserDTO userDTO) {
        User user = userRepository.findOne(userDTO.getId());
        List<Task> tasksAssigneToUser = taskRepository.findTasksByAssignee(user);
        return tasksAssigneToUser.parallelStream().map(task -> Transformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(task))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task newTask = new Task();
        BeanUtils.copyProperties(taskDTO, newTask);

        if(taskDTO.getAssignee() != null){
            newTask.setAssignee(userRepository.findOne(taskDTO.getAssignee().getId()));
        }
        newTask.setProject(projectRepository.findOne(taskDTO.getProject().getId()));

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
    public void removeTask(Long taskId) {
        taskRepository.delete(taskId);
    }
}
