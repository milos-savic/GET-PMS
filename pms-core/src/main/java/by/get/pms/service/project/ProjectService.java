package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;

import java.util.List;

/**
 * Created by milos on 19-Oct-16.
 */
public interface ProjectService {

    // for admin
    List<ProjectDTO> getAllProjects();

    // for pm
    List<ProjectDTO> getProjectManagerProjects(UserDTO projectManager);

    // for dev
    List<ProjectDTO> getProjectAvailableForDeveloper(UserDTO developer);

    // allowed to admin and pm
    UserDTO createProject(ProjectDTO projectDTO);

    // allowed to admin
    void updateProject(ProjectDTO projectDTO);

    // allowed to admin
    void deleteProject(Long projectId);
}
