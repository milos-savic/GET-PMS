package by.get.pms.service.task;

import by.get.pms.data.TaskData;
import by.get.pms.dataaccess.TaskDAO;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dtos.*;
import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.model.User;
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
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

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
		return Dto2DataTransformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(taskDao.findOne(taskId));
	}

	@Override
	public TaskDTO getTaskByProjectAndName(ProjectDTO projectDTO, String name) {
		Project project = projectRepository.findOne(projectDTO.getId());
		Task task = taskDao.findTaskByProjectAndName(project, name);
		return Dto2DataTransformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(task);
	}

	@Override
	public List<TaskDTO> getProjectTasks(ProjectDTO projectDTO) {
		Project project = projectRepository.findOne(projectDTO.getId());
		List<Task> projectTasks = taskDao.findTasksByProject(project);

		return projectTasks.parallelStream().map(Dto2DataTransformers.TASK_ENTITY_2_TASK_DTO_FUNCTION::apply)
				.collect(Collectors.toList());
	}

	@Override
	public List<TaskDTO> getTasksAssignedToUser(UserDTO userDTO) {
		User user = userRepository.findOne(userDTO.getId());
		List<Task> tasksAssigneToUser = taskDao.findTasksByAssignee(user);
		return tasksAssigneToUser.parallelStream().map(Dto2DataTransformers.TASK_ENTITY_2_TASK_DTO_FUNCTION::apply)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public TaskDTO createTask(TaskDTO taskDTO) {
		Task newTask = new Task();
		BeanUtils.copyProperties(taskDTO, newTask);

		if (taskDTO.getAssignee() != null) {
			newTask.setAssignee(userRepository.findOne(taskDTO.getAssignee().getId()));
		}
		newTask.setProject(projectRepository.findOne(taskDTO.getProject().getId()));

		newTask = taskDao.save(newTask);

		return Dto2DataTransformers.TASK_ENTITY_2_TASK_DTO_FUNCTION.apply(newTask);
	}

	@Override
	@Transactional
	public void updateTask(TaskDTO taskParams) {
		Task taskFromDb = taskDao.findOne(taskParams.getId());
		BeanUtils.copyProperties(taskParams, taskFromDb);
		taskFromDb.setAssignee(
				taskParams.getAssignee() == null ? null : userRepository.findOne(taskParams.getAssignee().getId()));
		taskFromDb.setProject(projectRepository.findOne(taskParams.getProject().getId()));
	}

	@Override
	@Transactional
	public void updateTaskByProjectManager(TaskUpdateParamsForPM taskUpdateParamsForPM) {
		Task taskFromDb = taskDao.findOne(taskUpdateParamsForPM.getId());
		BeanUtils.copyProperties(taskUpdateParamsForPM, taskFromDb);
		taskFromDb.setAssignee(taskUpdateParamsForPM.getAssignee() == null ?
				null :
				userRepository.findOne(taskUpdateParamsForPM.getAssignee().getId()));

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
