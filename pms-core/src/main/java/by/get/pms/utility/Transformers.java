package by.get.pms.utility;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.model.User;

import java.util.function.Function;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public final class Transformers {

	public static final Function<User, UserDTO> USER_ENTITY_2_USER_DTO_TRANSFORMER = user -> new UserDTO(user.getId(),
			user.getFirstName(), user.getLastName(), user.getEmail(),
			user.getUserAccount().getUsername(), user.getUserAccount().getCreationDateTime(),
			user.getUserAccount().isActive(), user.getUserAccount().getRole());

	public static final Function<Project, ProjectDTO> PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER = project -> new ProjectDTO(
			project.getId(), project.getCode(), project.getName(), project.getDescription(),
			USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(project.getProjectManager()));

	public static final Function<Task, TaskDTO> TASK_ENTITY_2_TASK_DTO_FUNCTION = task -> new TaskDTO(task.getId(),
			task.getName(), task.getTaskStatus(), task.getProgress(), task.getDeadline(), task.getDescription(),
			USER_ENTITY_2_USER_DTO_TRANSFORMER.apply(task.getAssignee()),
			PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER.apply(task.getProject()));
}
