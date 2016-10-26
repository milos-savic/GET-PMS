package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;

import java.util.List;

/**
 * Created by milos on 23-Oct-16.
 */
public interface ProjectFacade {

	ProjectDTO getProject(Long projectId);

	// for admin
	List<ProjectDTO> getAllProjects();

	// for pm
	List<ProjectDTO> getProjectsAvailableForPM(UserDTO projectManager);

	// for dev
	List<ProjectDTO> getProjectsAvailableForDeveloper(UserDTO developer);

	// allowed to admin and pm
	ProjectDTO createProject(ProjectDTO projectParams) throws ApplicationException;

	// allowed to admin
	void updateProject(ProjectDTO projectParams) throws ApplicationException;

	// allowed to admin
	void deleteProject(Long projectId) throws ApplicationException;
}
