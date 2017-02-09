package by.get.pms.facade.project;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.UserDTO;
import by.get.pms.exception.ApplicationException;

import java.util.List;

/**
 * Created by Milos.Savic on 2/9/2017.
 */
public interface ProjectFacade {

    List<ProjectDTO> getAll();

    ProjectDTO getProject(Long projectId);

    ProjectDTO createProject(ProjectDTO projectParams) throws ApplicationException;

    ProjectDTO createProjectByPM(UserDTO projectManager, ProjectDTO projectParams) throws ApplicationException;

    // allowed to admin
    void updateProject(ProjectDTO projectParams) throws ApplicationException;

    // allowed to admin
    void removeProject(Long projectId) throws ApplicationException;
}
