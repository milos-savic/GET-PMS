package by.get.pms.service.task;

import by.get.pms.data.ProjectData;
import by.get.pms.data.TaskData;
import by.get.pms.data.UserData;
import by.get.pms.dataaccess.TaskDAO;
import by.get.pms.dataaccess.UserDAO;
import by.get.pms.dtos.*;
import by.get.pms.model.Task;
import by.get.pms.utility.Data2DtoTransformers;
import by.get.pms.utility.Dto2DataTransformers;
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
class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDao;

    @Autowired
    private UserDAO userDao;

    @Override
    public List<TaskDTO> getAll() {
        List<TaskData> tasks = taskDao.findAll();
        return tasks.parallelStream().map(Data2DtoTransformers.TASK_DATA_2_TASK_DTO_FUNCTION::apply)
                .collect(Collectors.toList());
    }

    @Override
    public boolean taskExists(Long taskId) {
        return taskDao.exists(taskId);
    }

    @Override
    public boolean taskExistsByProjectAndName(ProjectDTO projectDto, String name) {
        return taskDao.taskExistsByProjectAndName(
                Dto2DataTransformers.PROJECT_DTO_2_PROJECT_DATA_TRANSFORMER.apply(projectDto), name);
    }

    @Override
    public TaskDTO getTask(Long taskId) {
        return Data2DtoTransformers.TASK_DATA_2_TASK_DTO_FUNCTION.apply(taskDao.findOne(taskId));
    }

    @Override
    public TaskDTO getTaskByProjectAndName(ProjectDTO projectDTO, String name) {
        ProjectData projectData = Dto2DataTransformers.PROJECT_DTO_2_PROJECT_DATA_TRANSFORMER.apply(projectDTO);
        return Data2DtoTransformers.TASK_DATA_2_TASK_DTO_FUNCTION.apply(taskDao.findTaskByProjectAndName(projectData, name));
    }

    @Override
    public List<TaskDTO> getProjectTasks(ProjectDTO projectDTO) {
        ProjectData projectData = Dto2DataTransformers.PROJECT_DTO_2_PROJECT_DATA_TRANSFORMER.apply(projectDTO);
        List<TaskData> projectTasks = taskDao.findTasksByProject(projectData);

        return projectTasks.parallelStream().map(Data2DtoTransformers.TASK_DATA_2_TASK_DTO_FUNCTION::apply)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksAssignedToUser(UserDTO userDTO) {
        UserData userData = Dto2DataTransformers.USER_DTO_2_USER_DATA_TRANSFORMER.apply(userDTO);
        List<TaskData> tasksAssignedToUser = taskDao.findTasksByAssignee(userData);
        return tasksAssignedToUser.parallelStream().map(Data2DtoTransformers.TASK_DATA_2_TASK_DTO_FUNCTION::apply)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskParams) {
        TaskData taskData = taskDao.create(Dto2DataTransformers.TASK_DTO_2_TASK_DATA_FUNCTION.apply(taskParams));
        return Data2DtoTransformers.TASK_DATA_2_TASK_DTO_FUNCTION.apply(taskData);
    }

    @Override
    @Transactional
    public void updateTask(TaskDTO taskParams) {
        taskDao.update(Dto2DataTransformers.TASK_DTO_2_TASK_DATA_FUNCTION.apply(taskParams));
    }

    @Override
    @Transactional
    public void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM) {
        Task taskFromDb = taskDao.findOne(taskUpdateParamsForPM.getId());
        BeanUtils.copyProperties(taskUpdateParamsForPM, taskFromDb);
        taskFromDb.setAssignee(taskUpdateParamsForPM.getAssignee() == null ?
                null :
                userDao.findOne(taskUpdateParamsForPM.getAssignee().getId()));

    }

    @Override
    @Transactional
    public void updateTaskByDeveloper(TaskUpdateParamsForDev taskUpdateParamsForDev) {
        Task taskFromDb = taskDao.findOne(taskUpdateParamsForDev.getId());
        BeanUtils.copyProperties(taskUpdateParamsForDev, taskFromDb);
    }

    @Override
    @Transactional
    public void removeTask(Long taskId) {
        taskDao.delete(taskId);
    }
}
