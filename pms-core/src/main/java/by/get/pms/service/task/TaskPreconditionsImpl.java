package by.get.pms.service.task;

import by.get.pms.dto.TaskDTO;
import by.get.pms.dto.TaskUpdateParamsForDev;
import by.get.pms.dto.TaskUpdateParamsForPM;
import by.get.pms.exception.ApplicationException;
import com.google.common.collect.Sets;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

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
			taskChangedProperties = taskPropertiesWithDifferentValues(taskFromDb, taskParams);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		taskChangedProperties.removeAll(taskPropertiesAllowedForChangeByPM());

		if (!taskChangedProperties.isEmpty()) {
			ApplicationException applicationException = new ApplicationException(
					"tasks.updateTask.NotAllowedChangeByPM");
			applicationException.setParams(new String[] { String.join(",", taskChangedProperties) });
			throw applicationException;
		}

	}

	@Override
	public void checkUpdateTaskByDeveloper(TaskDTO taskParams) throws ApplicationException{
		TaskDTO taskFromDb = taskService.getTaskByName(taskParams.getName());

		Set<String> taskChangedProperties;
		try {
			taskChangedProperties = taskPropertiesWithDifferentValues(taskFromDb, taskParams);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		taskChangedProperties.removeAll(taskPropertiesAllowedForChangeByDev());

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

	private Set<String> taskPropertiesAllowedForChangeByPM() {
		TaskUpdateParamsForPM taskUpdateParamsForPM = new TaskUpdateParamsForPM();
		BeanMap taskParamsForPMMap = new BeanMap(taskUpdateParamsForPM);
		Set<Object> taskParamsForPMPropNames = taskParamsForPMMap.keySet();

		return taskParamsForPMPropNames.parallelStream().map(o -> (String) o).collect(Collectors.toSet());
	}

	private Set<String> taskPropertiesAllowedForChangeByDev() {
		TaskUpdateParamsForDev taskUpdateParamsForDev = new TaskUpdateParamsForDev();
		BeanMap taskParamsForDevMap = new BeanMap(taskUpdateParamsForDev);
		Set<Object> taskParamsForDevPropNames = taskParamsForDevMap.keySet();

		return taskParamsForDevPropNames.parallelStream().map(o -> (String) o).collect(Collectors.toSet());
	}

	private Set<String> taskPropertiesWithDifferentValues(TaskDTO taskDTO1, TaskDTO taskDTO2)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Set<String> propertyNamesWithDiffValues = Sets.newHashSet();

		BeanMap map = new BeanMap(taskDTO1);
		PropertyUtilsBean propUtils = new PropertyUtilsBean();

		for (Object propNameObject : map.keySet()) {
			String propName = (String) propNameObject;
			Object propVal1 = propUtils.getProperty(taskDTO1, propName);
			Object propVal2 = propUtils.getProperty(taskDTO2, propName);
			if (!propVal1.equals(propVal2)) {
				propertyNamesWithDiffValues.add(propName);
			}
		}

		return propertyNamesWithDiffValues;
	}
}
