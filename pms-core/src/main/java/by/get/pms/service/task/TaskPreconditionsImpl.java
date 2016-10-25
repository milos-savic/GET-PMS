package by.get.pms.service.task;

import by.get.pms.dto.TaskDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.utility.TaskUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
@Component
public class TaskPreconditionsImpl implements TaskPreconditions {

	@Autowired
	private TaskService taskService;

	@Override
	public void checkCreateTaskPreconditions(TaskDTO taskParams) throws ApplicationException {

		if (taskParams.getDeadline().isBefore(LocalDate.now())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
			ApplicationException applicationException = new ApplicationException("tasks.createTask.DeadlineInPast");
			applicationException
					.setParams(new String[] { taskParams.getName(), taskParams.getDeadline().format(formatter) });
			throw applicationException;
		}

		if (taskExistsByName(taskParams.getName())) {
			ApplicationException applicationException = new ApplicationException("tasks.createTask.AlreadyExists");
			applicationException.setParams(new String[] { taskParams.getName() });
			throw applicationException;
		}
	}

	@Override
	public void checkUpdateTaskPreconditions(TaskDTO taskParams) throws ApplicationException {
		if (!taskService.taskExists(taskParams.getId())) {
			ApplicationException applicationException = new ApplicationException(
					"tasks.updateTask.NonExistingRecordForUpdate");
			applicationException.setParams(new String[] { taskParams.getId().toString() });
			throw applicationException;
		}

		if (taskExistsByName(taskParams.getName())) {
			TaskDTO task = taskService.getTaskByName(taskParams.getName());
			if (!task.getId().equals(taskParams.getId())) {
				ApplicationException applicationException = new ApplicationException("tasks.updateTask.AlreadyExists");
				applicationException.setParams(new String[] { taskParams.getName() });
				throw applicationException;
			}
		}
	}

	@Override
	public void checkUpdateTaskByProjectManager(TaskDTO taskParams) throws ApplicationException {
		TaskDTO taskFromDb = taskService.getTaskByName(taskParams.getName());

		Set<String> taskChangedProperties;
		try {
			taskChangedProperties = TaskUtilities.taskPropertiesWithDifferentValues(taskFromDb, taskParams);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		taskChangedProperties.removeAll(TaskUtilities.taskPropertiesAllowedForChangeByPM());

		if (!taskChangedProperties.isEmpty()) {
			ApplicationException applicationException = new ApplicationException(
					"tasks.updateTask.NotAllowedChangeByPM");
			applicationException.setParams(new String[] { String.join(",", taskChangedProperties) });
			throw applicationException;
		}

	}

	@Override
	public void checkUpdateTaskByDeveloper(TaskDTO taskParams) throws ApplicationException {
		TaskDTO taskFromDb = taskService.getTaskByName(taskParams.getName());

		Set<String> taskChangedProperties;
		try {
			taskChangedProperties = TaskUtilities.taskPropertiesWithDifferentValues(taskFromDb, taskParams);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		taskChangedProperties.removeAll(TaskUtilities.taskPropertiesAllowedForChangeByDev());

		if (!taskChangedProperties.isEmpty()) {
			ApplicationException applicationException = new ApplicationException(
					"tasks.updateTask.NotAllowedChangeByDev");
			applicationException.setParams(new String[] { String.join(",", taskChangedProperties) });
			throw applicationException;
		}
	}

	@Override
	public void checkRemoveTaskPreconditions(Long taskId) throws ApplicationException {
		if (taskId == null || !taskService.taskExists(taskId)) {
			ApplicationException applicationException = new ApplicationException(
					"tasks.removeTask.NonExistingRecordForRemove");
			applicationException.setParams(new String[] { taskId == null ? "null" : taskId.longValue() + "" });
			throw applicationException;
		}
	}

	private boolean taskExistsByName(String name) {
		return taskService.getTaskByName(name) != null;
	}
}
