package by.get.pms.service.task;

import by.get.pms.dto.TaskDTO;
import by.get.pms.exception.ApplicationException;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
public interface TaskPreconditions {

	void checkCreateTaskPreconditions(TaskDTO taskParams) throws ApplicationException;

	void checkUpdateTaskPreconditions(TaskDTO taskParams) throws ApplicationException;

	void checkRemoveTaskPreconditions(Long taskId) throws ApplicationException;
}
