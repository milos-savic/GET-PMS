package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;

/**
 * Created by milos on 23-Oct-16.
 */
public interface ProjectFacade {

    ProjectDTO getProject(Long projectId);

    boolean isProjecManagerOnProject(UserDTO projectManager, ProjectDTO project);
}
