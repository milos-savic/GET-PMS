package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;

import java.util.List;

/**
 * Created by milos on 19-Oct-16.
 */
public interface ProjectService {

	boolean projectExists(Long projectId);

	ProjectDTO getProject(Long projectId);

	ProjectDTO getProjectByCode(String projectCode);

	List<ProjectDTO> getAllProjects();

	/**
	 * List of projects on which @param projectManager is assigned as project manager.
	 */
	List<ProjectDTO> getProjectManagerProjects(UserDTO projectManager);

	List<ProjectDTO> getProjectsAvailableForPM(UserDTO projectManager);

	List<ProjectDTO> getProjectsAvailableForDeveloper(UserDTO developer);

	ProjectDTO createProject(ProjectDTO projectParams);

	void updateProject(ProjectDTO projectParams);

	void removeProject(Long projectId);
}
