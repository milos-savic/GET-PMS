package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
@Service
public class ProjectFacadeImpl implements ProjectFacade {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectPreconditions projectPreconditions;

	@Override
	public ProjectDTO getProject(Long projectId) {
		return projectService.getProject(projectId);
	}

	@Override
	public List<ProjectDTO> getProjectByIds(Set<Long> projectIds){
		return projectService.getProjectsByIds(projectIds);
	}

	@Override
	public ProjectDTO createProject(ProjectDTO projectParams) throws ApplicationException {
		projectPreconditions.checkCreateProjectPreconditions(projectParams);
		return projectService.createProject(projectParams);
	}

	@Override
	public ProjectDTO createProjectByPM(UserDTO projectManager, ProjectDTO projectParams) throws ApplicationException{
		projectPreconditions.checkCreateProjectByPMPreconditions(projectManager, projectParams);
		return projectService.createProject(projectParams);
	}

	@Override
	public void updateProject(ProjectDTO projectParams) throws ApplicationException {
		projectPreconditions.checkUpdateProjectPreconditions(projectParams);
		projectService.updateProject(projectParams);
	}

	@Override
	public void removeProject(Long projectId) throws ApplicationException {
		projectPreconditions.checkRemoveProjectPreconditions(projectId);
		projectService.removeProject(projectId);
	}
}
