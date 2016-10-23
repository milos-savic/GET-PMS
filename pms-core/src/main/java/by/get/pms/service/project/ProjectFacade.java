package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;

/**
 * Created by milos on 23-Oct-16.
 */
public interface ProjectFacade {

    ProjectDTO getProject(Long projectId);
}
