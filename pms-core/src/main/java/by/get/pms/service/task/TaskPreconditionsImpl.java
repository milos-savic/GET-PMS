package by.get.pms.service.task;

import by.get.pms.dto.TaskDTO;
import by.get.pms.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
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
