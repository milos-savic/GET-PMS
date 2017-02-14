package by.get.pms.utility;

import by.get.pms.data.ProjectData;
import by.get.pms.data.TaskData;
import by.get.pms.data.UserData;
import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.model.User;

import java.util.function.Function;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public final class Transformers {

	public static final Function<User, UserData> USER_ENTITY_2_USER_DATA_TRANSFORMER = user -> new UserData(
			user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserAccount().getUserName(),
			user.getUserAccount().getCreationDateTime(), user.getUserAccount().isActive(),
			user.getUserAccount().getRole());

	public static final Function<Project, ProjectData> PROJECT_ENTITY_2_PROJECT_DATA_TRANSFORMER = project -> new ProjectData(
			project.getId(), project.getCode(), project.getName(), project.getDescription(),
			USER_ENTITY_2_USER_DATA_TRANSFORMER.apply(project.getProjectManager()));

	public static final Function<Task, TaskData> TASK_ENTITY_2_TASK_DATA_TRANSFORMER = task -> new TaskData(task.getId(),
			task.getName(), task.getTaskStatus(), task.getProgress(), task.getDeadline(), task.getDescription(),
			task.getAssignee() != null ? USER_ENTITY_2_USER_DATA_TRANSFORMER.apply(task.getAssignee()) : null,
			PROJECT_ENTITY_2_PROJECT_DATA_TRANSFORMER.apply(task.getProject()));
}
