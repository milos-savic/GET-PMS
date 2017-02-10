package by.get.pms.utility;

import by.get.pms.data.*;
import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.TaskDTO;
import by.get.pms.dtos.UserDTO;

import java.util.function.Function;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public final class Dto2DataTransformers {

	public static final Function<UserDTO, UserData> USER_DTO_2_USER_DATA_TRANSFORMER = userDto -> new UserData(
			userDto.getId(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
			userDto.getUserName(), userDto.getCreationDate(), userDto.getActive(),
			UserRole.make(userDto.getRole().name()));

	public static final Function<ProjectDTO, ProjectData> PROJECT_DTO_2_PROJECT_DATA_TRANSFORMER = projectDto -> new ProjectData(
			projectDto.getId(), projectDto.getCode(), projectDto.getName(), projectDto.getDescription(),
			USER_DTO_2_USER_DATA_TRANSFORMER.apply(projectDto.getProjectManager()));

	public static final Function<TaskDTO, TaskData> TASK_DTO_2_TASK_DATA_FUNCTION = taskDto -> new TaskData(
			taskDto.getId(), taskDto.getName(), TaskStatus.make(taskDto.getTaskStatus().name()),
			taskDto.getProgress(), taskDto.getDeadline(), taskDto.getDescription(),
			taskDto.getAssignee() != null ? USER_DTO_2_USER_DATA_TRANSFORMER.apply(taskDto.getAssignee()) : null,
			PROJECT_DTO_2_PROJECT_DATA_TRANSFORMER.apply(taskDto.getProject()));
}
