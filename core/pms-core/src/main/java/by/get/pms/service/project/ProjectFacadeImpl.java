package by.get.pms.service.project;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.facade.project.ProjectFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
@Service
class ProjectFacadeImpl implements ProjectFacade {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectPreconditions projectPreconditions;


	@Override
	public List<ProjectDTO> getAll() {
		return projectService.getAll();
	}

	@Override
	public ProjectDTO getProject(Long projectId) {
		return projectService.getProject(projectId);
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
