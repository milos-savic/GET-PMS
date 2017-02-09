package by.get.pms.service.task;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.dto.TaskUpdateParamsForDev;
import by.get.pms.dto.TaskUpdateParamsForPM;
import by.get.pms.exception.ApplicationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
@Service
class TaskFacadeImpl implements TaskFacade {

	@Autowired
	private TaskPreconditions taskPreconditions;

	@Autowired
	private TaskService taskService;

	@Override
	public TaskDTO getTask(Long taskId) {
		return taskService.getTask(taskId);
	}

	@Override
	public List<TaskDTO> getProjectTasks(ProjectDTO projectDTO) {
		return taskService.getProjectTasks(projectDTO);
	}

	@Override
	public TaskDTO createTask(TaskDTO taskParams) throws ApplicationException {
		taskPreconditions.checkCreateTaskPreconditions(taskParams);
		return taskService.createTask(taskParams);
	}

	@Override
	public void updateTask(TaskDTO taskParams) throws ApplicationException {
		taskPreconditions.checkUpdateTaskPreconditions(taskParams);
		taskService.updateTask(taskParams);
	}

	@Override
	public void updateTaskByProjectManager(TaskDTO taskParams) throws ApplicationException {
		taskPreconditions.checkUpdateTaskPreconditions(taskParams);
		taskPreconditions.checkUpdateTaskByProjectManager(taskParams);
		TaskUpdateParamsForPM taskUpdateParamsForPM = new TaskUpdateParamsForPM();
		BeanUtils.copyProperties(taskParams, taskUpdateParamsForPM);
		taskService.updateTaskByProjectManager(taskUpdateParamsForPM);
	}

	@Override
	public void updateTaskByDeveloper(TaskDTO taskParams) throws ApplicationException {
		taskPreconditions.checkUpdateTaskPreconditions(taskParams);
		taskPreconditions.checkUpdateTaskByDeveloper(taskParams);
		TaskUpdateParamsForDev taskUpdateParamsForDev = new TaskUpdateParamsForDev();
		BeanUtils.copyProperties(taskParams, taskUpdateParamsForDev);
		taskService.updateTaskByDeveloper(taskUpdateParamsForDev);
	}

	@Override
	public void removeTask(Long taskId) throws ApplicationException {
		taskPreconditions.checkRemoveTaskPreconditions(taskId);
		taskService.removeTask(taskId);
	}
}
