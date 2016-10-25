package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;

import java.util.List;

/**
 * Created by milos on 19-Oct-16.
 */
public interface ProjectService {

	ProjectDTO getProject(Long projectId);

	// for admin
	List<ProjectDTO> getAllProjects();

	/**
	 * List of projects on which @param projectManager is assigned as project manager.
	 */
	List<ProjectDTO> getProjectManagerProjects(UserDTO projectManager);

	// for pm
	List<ProjectDTO> getProjectsAvailableForPM(UserDTO projectManager);

	// for dev
	List<ProjectDTO> getProjectsAvailableForDeveloper(UserDTO developer);

	// allowed to admin and pm
	ProjectDTO createProject(ProjectDTO projectParams);

	// allowed to admin
	void updateProject(ProjectDTO projectParams);

	// allowed to admin
	void deleteProject(Long projectId);
}
