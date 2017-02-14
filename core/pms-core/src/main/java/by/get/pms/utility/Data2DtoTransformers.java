package by.get.pms.utility;

import by.get.pms.data.ProjectData;
import by.get.pms.data.TaskData;
import by.get.pms.data.UserData;
import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.TaskDTO;
import by.get.pms.dtos.UserDTO;

import java.util.function.Function;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public class Data2DtoTransformers {
	public static final Function<UserData, UserDTO> USER_DATA_2_USER_DTO_TRANSFORMER = userData -> new UserDTO(
			userData.getId(), userData.getFirstName(), userData.getLastName(), userData.getEmail(),
			userData.getUserName(), userData.getCreationDate(), userData.getActive(),
			by.get.pms.dtos.UserRole.make(userData.getRole().name()));

	public static final Function<ProjectData, ProjectDTO> PROJECT_DATA_2_PROJECT_DTO_TRANSFORMER = projectData -> new ProjectDTO(
			projectData.getId(), projectData.getCode(), projectData.getName(), projectData.getDescription(),
			USER_DATA_2_USER_DTO_TRANSFORMER.apply(projectData.getProjectManager()));

	public static final Function<TaskData, TaskDTO> TASK_DATA_2_TASK_DTO_TRANSORMER = taskData -> new TaskDTO(
			taskData.getId(), taskData.getName(), by.get.pms.dtos.TaskStatus.make(taskData.getTaskStatus().name()),
			taskData.getProgress(), taskData.getDeadline(), taskData.getDescription(),
			taskData.getAssignee() != null ? USER_DATA_2_USER_DTO_TRANSFORMER.apply(taskData.getAssignee()) : null,
			PROJECT_DATA_2_PROJECT_DTO_TRANSFORMER.apply(taskData.getProject()));
}
