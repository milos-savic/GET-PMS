package by.get.pms.service.project;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/26/2016.
 */
@Component
public class ProjectPreconditionsImpl implements ProjectPreconditions {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Override
    public void checkCreateProjectPreconditions(ProjectDTO projectParams) throws ApplicationException {
        if (projectExistsByCode(projectParams.getCode())) {
            ApplicationException applicationException = new ApplicationException("projects.createProject.AlreadyExists");
            applicationException.setParams(new String[]{projectParams.getCode()});
            throw applicationException;
        }
    }

    @Override
    public void checkCreateProjectByPMPreconditions(UserDTO projectManager, ProjectDTO projectParams) throws ApplicationException {
        if (!projectManager.equals(projectParams.getProjectManager())) {
            ApplicationException applicationException = new ApplicationException("projects.createProject.pmDiffThanCallerPm");
            applicationException.setParams(new String[]{projectManager.getUserName(), projectParams.getProjectManager().getUserName()});
            throw applicationException;
        }
    }

    @Override
    public void checkUpdateProjectPreconditions(ProjectDTO projectParams) throws ApplicationException {
        if (!projectService.projectExists(projectParams.getId())) {
            ApplicationException applicationException = new ApplicationException(
                    "projects.updateProject.NonExistingRecordForUpdate");
            applicationException.setParams(new String[]{projectParams.getId().toString()});
            throw applicationException;
        }

        if (projectExistsByCode(projectParams.getCode())) {
            ProjectDTO projectDTO = projectService.getProjectByCode(projectParams.getCode());
            if (!projectDTO.getId().equals(projectParams.getId())) {
                ApplicationException applicationException = new ApplicationException("projects.updateProject.AlreadyExists");
                applicationException.setParams(new String[]{projectParams.getCode()});
                throw applicationException;
            }
        }
    }

    @Override
    public void checkRemoveProjectPreconditions(Long projectId) throws ApplicationException {
        if (projectId == null || !projectService.projectExists(projectId)) {
            ApplicationException applicationException = new ApplicationException(
                    "projects.removeProject.NonExistingRecordForRemove");
            applicationException.setParams(new String[]{projectId == null ? "null" : projectId + ""});
            throw applicationException;
        }

        // precondition for referential integrities (project has tasks)
        ProjectDTO project = projectService.getProject(projectId);
        List<TaskDTO> projectTasks = taskService.getProjectTasks(project);
        if (!projectTasks.isEmpty()) {
            ApplicationException applicationException = new ApplicationException("projects.removeProject.projectWithTasks");
            applicationException.setParams(new String[]{String.join(", ",
                    projectTasks.parallelStream().map(TaskDTO::getName).collect(Collectors.toList()))});
            throw applicationException;
        }
    }

    // TODO: change to count(*) check
    private boolean projectExistsByCode(String projectCode) {
        return projectService.projectExistsByCode(projectCode);
    }
}
