package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by milos on 19-Oct-16.
 */
public interface ProjectService {


    List<ProjectDTO> getAll();

    boolean projectExists(Long projectId);

    boolean projectExistsByCode(String projectCode);

    ProjectDTO getProject(Long projectId);

    ProjectDTO getProjectByCode(String projectCode);

    List<ProjectDTO> getProjectsByIds(Set<Long> projectIds);

    /**
     * List of projects on which @param projectManager is assigned as project manager.
     */
    List<ProjectDTO> getProjectManagerProjects(UserDTO projectManager);

    ProjectDTO createProject(ProjectDTO projectParams);

    void updateProject(ProjectDTO projectParams);

    void removeProject(Long projectId);
}
