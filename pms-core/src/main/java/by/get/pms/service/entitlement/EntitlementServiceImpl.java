package by.get.pms.service.entitlement;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.dataaccess.TaskRepository;
import by.get.pms.dataaccess.UserAccountRepository;
import by.get.pms.dto.EntitlementDTO;
import by.get.pms.dto.ObjectType;
import by.get.pms.model.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/27/2016.
 */
@Service
public class EntitlementServiceImpl implements EntitlementService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<EntitlementDTO> getEntitlementsForObjectTypePermittedToUser(String userName, ObjectType objectType) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(userName);

        switch (objectType) {
            case TASK:
                return getEntitlementsForTasksPermittedToUser(userAccount);
            case PROJECT:
                return getEntitlementsForProjectsPermittedToUser(userAccount);
            default:
                throw new RuntimeException("Unsupported object type: " + objectType.name());
        }
    }

    private List<EntitlementDTO> getEntitlementsForTasksPermittedToUser(UserAccount userAccount) {
        UserRole userRole = userAccount.getRole();
        switch (userRole) {
            case ADMIN:
                return getEntitlementsForTasksPermittedToAdmin(userAccount);
            case PROJECT_MANAGER:
                return getEntitlementsForTasksPermittedToPM(userAccount);
            case DEV:
                return getEntitlementsForTasksPermittedToDev(userAccount);
            case GUEST:
                return new ArrayList<>();
            default:
                throw new RuntimeException("Unsupported user role: " + userRole.name());
        }
    }

    private List<EntitlementDTO> getEntitlementsForProjectsPermittedToUser(UserAccount userAccount) {
        UserRole userRole = userAccount.getRole();
        switch (userRole) {
            case ADMIN:
                return getEntitlementsForProjectsPermittedToAdmin(userAccount);
            case PROJECT_MANAGER:
                return getEntitlementsForProjectsPermittedToPM(userAccount);
            case DEV:
                return getEntitlementsForProjectsPermittedToDev(userAccount);
            case GUEST:
                return new ArrayList<>();
            default:
                throw new RuntimeException("Unsupported user role: " + userRole.name());
        }
    }

    // TASKS //////////////////////////////////////////////////////////////////////////////////////////

    private List<EntitlementDTO> getEntitlementsForTasksPermittedToAdmin(UserAccount adminAccount) {
        List<Task> allTasks = Lists.newArrayList(taskRepository.findAll());
        return allTasks.parallelStream()
                .map(task -> new EntitlementDTO(task.getId(), ObjectType.TASK, adminAccount.getId(), true))
                .collect(Collectors.toList());
    }

    private List<EntitlementDTO> getEntitlementsForTasksPermittedToPM(UserAccount pmAccount) {
        List<Task> tasksPermittedToPm = Lists.newLinkedList();

        List<Project> pmProjects = projectRepository.findProjectsByProjectManager(pmAccount.getUser());
        pmProjects.forEach(pmProject -> tasksPermittedToPm.addAll(taskRepository.findTasksByProject(pmProject)));

        Set<Task> allTasks = Sets.newHashSet(taskRepository.findAll());
        allTasks.removeAll(tasksPermittedToPm);
        Set<Task> tasksAvailableToPMFromOtherProjects = filterTasksAvailableForUser(allTasks, pmAccount.getUser());

        tasksPermittedToPm.addAll(tasksAvailableToPMFromOtherProjects);

        return tasksPermittedToPm.parallelStream()
                .map(task -> new EntitlementDTO(task.getId(), ObjectType.TASK, pmAccount.getId(), true))
                .collect(Collectors.toList());
    }

    private Set<Task> filterTasksAvailableForUser(Set<Task> tasks, User user) {
        return tasks.parallelStream().filter(task -> (task.getAssignee() == null) || user.equals(task.getAssignee()))
                .collect(Collectors.toSet());
    }

    private List<EntitlementDTO> getEntitlementsForTasksPermittedToDev(UserAccount devAccount) {
        Set<Task> allTasks = Sets.newHashSet(taskRepository.findAll());
        Set<Task> tasksPermittedToDev = filterTasksAvailableForUser(allTasks, devAccount.getUser());

        return tasksPermittedToDev.parallelStream()
                .map(task -> new EntitlementDTO(task.getId(), ObjectType.TASK, devAccount.getId(), true))
                .collect(Collectors.toList());
    }

    // PROJECTS ///////////////////////////////////////////////////////////////////////////////////////

    private List<EntitlementDTO> getEntitlementsForProjectsPermittedToAdmin(UserAccount adminAccount) {
        List<Project> allProjects = Lists.newArrayList(projectRepository.findAll());

        return allProjects.parallelStream()
                .map(project -> new EntitlementDTO(project.getId(), ObjectType.PROJECT, adminAccount.getId(), true))
                .collect(Collectors.toList());
    }

    private List<EntitlementDTO> getEntitlementsForProjectsPermittedToPM(UserAccount pmAccount) {
        List<EntitlementDTO> entitlementsForTaksPermittedToPm = getEntitlementsForTasksPermittedToPM(pmAccount);

        Set<Long> taskIds = entitlementsForTaksPermittedToPm.parallelStream().map(EntitlementDTO::getObjectid)
                .collect(Collectors.toSet());
        List<Task> tasksPermittedToPM = taskRepository.findTasksByIds(taskIds);

        List<Project> projectsPermittedToPM = tasksPermittedToPM.stream().map(Task::getProject).distinct()
                .collect(Collectors.toList());

        return projectsPermittedToPM.parallelStream()
                .map(project -> new EntitlementDTO(project.getId(), ObjectType.PROJECT, pmAccount.getId(), true))
                .collect(Collectors.toList());

    }

    private List<EntitlementDTO> getEntitlementsForProjectsPermittedToDev(UserAccount devAccount) {
        List<EntitlementDTO> entitlementsForTaksPermittedToDev = getEntitlementsForTasksPermittedToDev(devAccount);

        Set<Long> taskIds = entitlementsForTaksPermittedToDev.parallelStream().map(EntitlementDTO::getObjectid)
                .collect(Collectors.toSet());
        List<Task> tasksPermittedToPM = taskRepository.findTasksByIds(taskIds);

        List<Project> projectsPermittedToDev = tasksPermittedToPM.stream().map(Task::getProject).distinct()
                .collect(Collectors.toList());

        return projectsPermittedToDev.parallelStream()
                .map(project -> new EntitlementDTO(project.getId(), ObjectType.PROJECT, devAccount.getId(), true))
                .collect(Collectors.toList());
    }
}
