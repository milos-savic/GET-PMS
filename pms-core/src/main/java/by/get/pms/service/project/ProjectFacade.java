package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;

import java.util.List;
import java.util.Set;

/**
 * Created by milos on 23-Oct-16.
 */
public interface ProjectFacade {

	List<ProjectDTO> getAll();

	ProjectDTO getProject(Long projectId);

	List<ProjectDTO> getProjectByIds(Set<Long> projectIds);

	ProjectDTO createProject(ProjectDTO projectParams) throws ApplicationException;

	ProjectDTO createProjectByPM(UserDTO projectManager, ProjectDTO projectParams) throws ApplicationException;

	// allowed to admin
	void updateProject(ProjectDTO projectParams) throws ApplicationException;

	// allowed to admin
	void removeProject(Long projectId) throws ApplicationException;
}
