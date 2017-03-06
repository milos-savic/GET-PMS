package by.get.pms.dataaccess;

import by.get.pms.data.ProjectData;
import by.get.pms.data.TaskData;
import by.get.pms.data.UserData;
import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.model.TaskStatus;
import by.get.pms.model.User;
import by.get.pms.springdata.ProjectRepository;
import by.get.pms.springdata.TaskRepository;
import by.get.pms.springdata.UserRepository;
import by.get.pms.utility.Transformers;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
@Repository
class TaskDAOImpl implements TaskDAO {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean exists(Long taskId) {
        return taskRepository.exists(taskId);
    }

    @Override
    public List<TaskData> findAll() {
        List<Task> tasks = Lists.newArrayList(taskRepository.findAll());
        return tasks.parallelStream().map(Transformers.TASK_ENTITY_2_TASK_DATA_TRANSFORMER::apply)
                .collect(Collectors.toList());
    }

    @Override
    public TaskData findOne(Long taskId) {
        return Transformers.TASK_ENTITY_2_TASK_DATA_TRANSFORMER.apply(taskRepository.findOne(taskId));
    }

    @Override
    public TaskData findTaskByProjectAndName(ProjectData projectData, String name) {
        Project project = projectRepository.findOne(projectData.getId());
        Task task = taskRepository.findTaskByProjectAndName(project, name);
        return Transformers.TASK_ENTITY_2_TASK_DATA_TRANSFORMER.apply(task);
    }

    @Override
    public List<TaskData> findTasksByAssignee(UserData assignee) {
        User assigneeUser = userRepository.findOne(assignee.getId());
        List<Task> assigneeTasks = taskRepository.findTasksByAssignee(assigneeUser);

        return assigneeTasks.parallelStream().map(Transformers.TASK_ENTITY_2_TASK_DATA_TRANSFORMER::apply)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskData> findTasksByProject(ProjectData projectData) {
        Project project = projectRepository.findOne(projectData.getId());
        List<Task> projectTasks = taskRepository.findTasksByProject(project);

        return projectTasks.parallelStream().map(Transformers.TASK_ENTITY_2_TASK_DATA_TRANSFORMER::apply)
                .collect(Collectors.toList());
    }

    @Override
    public TaskData create(TaskData taskParams) {
        Task newTask = new Task();
        BeanUtils.copyProperties(taskParams, newTask);

        if (taskParams.getAssignee() != null) {
            newTask.setAssignee(userRepository.findOne(taskParams.getAssignee().getId()));
        }
        newTask.setProject(projectRepository.findOne(taskParams.getProject().getId()));

        newTask = taskRepository.save(newTask);

        return Transformers.TASK_ENTITY_2_TASK_DATA_TRANSFORMER.apply(newTask);
    }

    @Override
    public void update(TaskData taskParams) {
        Task taskFromDb = taskRepository.findOne(taskParams.getId());
        BeanUtils.copyProperties(taskParams, taskFromDb);
        taskFromDb.setAssignee(
                taskParams.getAssignee() == null ? null : userRepository.findOne(taskParams.getAssignee().getId()));
        taskFromDb.setProject(projectRepository.findOne(taskParams.getProject().getId()));
        taskFromDb.setTaskStatus(TaskStatus.make(taskParams.getTaskStatus().name()));
    }

    @Override
    public void delete(Long taskId) {
        taskRepository.delete(taskId);
    }

    @Override
    public boolean taskExistsByProjectAndName(ProjectData projectData, String name) {
        Project project = projectRepository.findOne(projectData.getId());
        return taskRepository.taskExistsByProjectAndName(project, name) > 0;
    }
}
