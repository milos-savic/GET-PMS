package by.get.pms.service.user;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.service.project.ProjectService;
import by.get.pms.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
@Component
class UserPreconditionsImpl implements UserPreconditions {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Override
    public void checkCreateUserPreconditions(UserDTO userParams) throws ApplicationException {
        if (userExistsByUsername(userParams.getUserName())) {
            ApplicationException applicationException = new ApplicationException("users.createUser.AlreadyExists");
            applicationException.setParams(new String[]{userParams.getUserName()});
            throw applicationException;
        }
    }

    @Override
    public void checkUpdateUserPreconditions(UserDTO userParams) throws ApplicationException {

        if (!userService.userExists(userParams.getId())) {
            ApplicationException applicationException = new ApplicationException(
                    "users.updateUser.NonExistingRecordForUpdate");
            applicationException.setParams(new String[]{userParams.getId().toString()});
            throw applicationException;
        }

        // when we udpate username and that username is in use (some other user - different id)
        if (userExistsByUsername(userParams.getUserName())) {
            UserDTO user = userService.getUserByUserName(userParams.getUserName());
            if (!user.getId().equals(userParams.getId())) {
                ApplicationException applicationException = new ApplicationException("users.updateUser.AlreadyExists");
                applicationException.setParams(new String[]{userParams.getUserName()});
                throw applicationException;
            }
        }

        // precondition for role update;
        // situation: PM -> ROLE_DEV, ROLE_ADMIN and PM is assigned to project
        if (userParams.isProjectManager())
            return;

        UserDTO projectManager = userService.getUser(userParams.getId());
        if (!projectManager.isProjectManager())
            return;

        List<ProjectDTO> projectManagerProjects = projectService.getProjectManagerProjects(projectManager);

        if (!projectManagerProjects.isEmpty()) {
            ApplicationException applicationException = new ApplicationException("users.updateUser.roleUpdate");
            applicationException.setParams(new String[]{userParams.getRole().name(), String.join(", ",
                    projectManagerProjects.parallelStream().map(ProjectDTO::getName).collect(Collectors.toList()))});
            throw applicationException;
        }
    }

    @Override
    public void checkRemoveUserPreconditions(Long userId) throws ApplicationException {
        if (userId == null || !userService.userExists(userId)) {
            ApplicationException applicationException = new ApplicationException(
                    "users.removeUser.NonExistingRecordForRemove");
            applicationException.setParams(new String[]{userId == null ? "null" : userId + ""});
            throw applicationException;
        }

        // precondition for referential integrities (user assigned to task, pm assigned to project)
        UserDTO user = userService.getUser(userId);

        List<TaskDTO> tasksAssignedToUser = taskService.getTasksAssignedToUser(user);
        if (!tasksAssignedToUser.isEmpty()) {
            ApplicationException applicationException = new ApplicationException("users.removeUser.userWithTasks");
            applicationException.setParams(new String[]{String.join(", ",
                    tasksAssignedToUser.parallelStream().map(TaskDTO::getName).collect(Collectors.toList()))});
            throw applicationException;
        }

        if (user.isProjectManager()) {
            List<ProjectDTO> projectManagerProjects = projectService.getProjectManagerProjects(user);
            if (!projectManagerProjects.isEmpty()) {
                ApplicationException applicationException = new ApplicationException("users.removeUser.pmWithProjects");
                applicationException.setParams(new String[]{String.join(", ",
                        projectManagerProjects.parallelStream().map(ProjectDTO::getName)
                                .collect(Collectors.toList()))});
                throw applicationException;
            }
        }
    }

    private boolean userExistsByUsername(String username) {
        return userService.userExistsByUserName(username);
    }
}
