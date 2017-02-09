package by.get.pms.service.task;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.TaskDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.dtos.TaskStatus;
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
class TaskPreconditionsImpl implements TaskPreconditions {

    @Autowired
    private TaskService taskService;

    @Override
    public void checkCreateTaskPreconditions(TaskDTO taskParams) throws ApplicationException {

        if (taskParams.getDeadline().isBefore(LocalDate.now())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            ApplicationException applicationException = new ApplicationException("tasks.createTask.DeadlineInPast");
            applicationException
                    .setParams(new String[]{taskParams.getName(), taskParams.getDeadline().format(formatter)});
            throw applicationException;
        }

        if (taskExistsByProjectAndName(taskParams.getProject(), taskParams.getName())) {
            ApplicationException applicationException = new ApplicationException("tasks.createTask.AlreadyExists");
            applicationException.setParams(new String[]{taskParams.getName()});
            throw applicationException;
        }

        if (!checkStatusAndProgressAreAligned(taskParams.getTaskStatus(), taskParams.getProgress())) {
            ApplicationException applicationException = new ApplicationException(
                    "tasks.createTask.StatusAndProgressNotAligned");
            applicationException
                    .setParams(new String[]{taskParams.getTaskStatus().name(), "" + taskParams.getProgress()});
            throw applicationException;
        }
    }

    private boolean checkStatusAndProgressAreAligned(TaskStatus taskStatus, int progress) {
        switch (taskStatus) {
            case NEW:
                return progress == 0;
            case IN_PROGRESS:
                return progress > 0 && progress < 100;
            case FINISHED:
                return progress == 100;
            default:
                throw new RuntimeException("Unsupported status: " + taskStatus.name());
        }
    }

    @Override
    public void checkUpdateTaskPreconditions(TaskDTO taskParams) throws ApplicationException {
        if (!taskService.taskExists(taskParams.getId())) {
            ApplicationException applicationException = new ApplicationException(
                    "tasks.updateTask.NonExistingRecordForUpdate");
            applicationException.setParams(new String[]{taskParams.getId().toString()});
            throw applicationException;
        }

        if (taskExistsByProjectAndName(taskParams.getProject(), taskParams.getName())) {
            TaskDTO task = taskService.getTaskByProjectAndName(taskParams.getProject(), taskParams.getName());
            if (!task.getId().equals(taskParams.getId())) {
                ApplicationException applicationException = new ApplicationException("tasks.updateTask.AlreadyExists");
                applicationException.setParams(new String[]{taskParams.getName()});
                throw applicationException;
            }
        }

        if (!checkStatusAndProgressAreAligned(taskParams.getTaskStatus(), taskParams.getProgress())) {
            ApplicationException applicationException = new ApplicationException(
                    "tasks.updateTask.StatusAndProgressNotAligned");
            applicationException
                    .setParams(new String[]{taskParams.getTaskStatus().name(), "" + taskParams.getProgress()});
            throw applicationException;
        }
    }

    @Override
    public void checkUpdateTaskByProjectManager(TaskDTO taskParams) throws ApplicationException {
        TaskDTO taskFromDb = taskService.getTaskByProjectAndName(taskParams.getProject(), taskParams.getName());

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
            applicationException.setParams(new String[]{String.join(",", taskChangedProperties)});
            throw applicationException;
        }

    }

    @Override
    public void checkUpdateTaskByDeveloper(TaskDTO taskParams) throws ApplicationException {
        TaskDTO taskFromDb = taskService.getTaskByProjectAndName(taskParams.getProject(), taskParams.getName());

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
            applicationException.setParams(new String[]{String.join(",", taskChangedProperties)});
            throw applicationException;
        }
    }

    @Override
    public void checkRemoveTaskPreconditions(Long taskId) throws ApplicationException {
        if (taskId == null || !taskService.taskExists(taskId)) {
            ApplicationException applicationException = new ApplicationException(
                    "tasks.removeTask.NonExistingRecordForRemove");
            applicationException.setParams(new String[]{taskId == null ? "null" : taskId + ""});
            throw applicationException;
        }
    }

    private boolean taskExistsByProjectAndName(ProjectDTO projectDTO, String name) {
        return taskService.taskExistsByProjectAndName(projectDTO, name);
    }
}
