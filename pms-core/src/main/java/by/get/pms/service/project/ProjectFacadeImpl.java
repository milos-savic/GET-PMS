package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Milos.Savic on 10/24/2016.
 */
@Service
public class ProjectFacadeImpl implements ProjectFacade{

	@Autowired
	private ProjectService projectService;

	@Override
	public ProjectDTO getProject(Long projectId){
		return projectService.getProject(projectId);
	}

	@Override
	public boolean isProjecManagerOnProject(UserDTO projectManager, ProjectDTO project){
		return false;
	}
}
