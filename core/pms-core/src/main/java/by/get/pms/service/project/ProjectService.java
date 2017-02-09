package by.get.pms.service.project;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.UserDTO;

import java.util.List;

/**
 * Created by milos on 19-Oct-16.
 */
public interface ProjectService {

	List<ProjectDTO> getAll();

	boolean projectExists(Long projectId);

	boolean projectExistsByCode(String projectCode);

	ProjectDTO getProject(Long projectId);

	ProjectDTO getProjectByCode(String projectCode);

	/**
	 * List of projects on which @param projectManager is assigned as project manager.
	 */
	List<ProjectDTO> getProjectManagerProjects(UserDTO projectManager);

	ProjectDTO createProject(ProjectDTO projectParams);

	void updateProject(ProjectDTO projectParams);

	void removeProject(Long projectId);
}
