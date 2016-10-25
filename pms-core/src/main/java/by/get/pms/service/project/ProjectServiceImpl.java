package by.get.pms.service.project;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.model.Project;
import by.get.pms.service.task.TaskService;
import by.get.pms.utility.Transformers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/21/2016.
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @Override
    public ProjectDTO getProject(Long projectId) {
        return Transformers.PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER.apply(projectRepository.findOne(projectId));
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = Lists.newArrayList(projectRepository.findAll());
        return projects.parallelStream()
                .map(project -> Transformers.PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER.apply(project))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getProjectManagerProjects(UserDTO projectManager) {
        List<Project> projectManagerProjects = projectRepository
                .findProjectManagerProjects(userRepository.findOne(projectManager.getId()));

        return projectManagerProjects.parallelStream()
                .map(project -> Transformers.PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER.apply(project))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getProjectsAvailableForPM(UserDTO projectManager) {
        Set<ProjectDTO> allProjects = Sets.newHashSet(getAllProjects());
        Set<ProjectDTO> projectManagerProjects = Sets.newHashSet(getProjectManagerProjects(projectManager));

        allProjects.removeAll(projectManagerProjects);

        Set<ProjectDTO> projectsAvailableToPMBasedOnTasks = allProjects.parallelStream()
                .filter(projectDTO -> !taskService.getProjectTasksAvailableForPM(projectDTO, projectManager).isEmpty())
                .collect(Collectors.toSet());

        projectManagerProjects.addAll(projectsAvailableToPMBasedOnTasks);
        return Lists.newArrayList(projectManagerProjects);
    }

    @Override
    public List<ProjectDTO> getProjectsAvailableForDeveloper(UserDTO developer) {
        Set<ProjectDTO> allProjects = Sets.newHashSet(getAllProjects());

        return allProjects.parallelStream()
                .filter(projectDTO -> !taskService.getProjectTasksAvailableForDeveloper(projectDTO, developer).isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectDTO createProject(ProjectDTO projectParams) {
        Project newProject = new Project();
        BeanUtils.copyProperties(projectParams, newProject);
        newProject = projectRepository.save(newProject);

        return Transformers.PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER.apply(newProject);
    }

    @Override
    @Transactional
    public void updateProject(ProjectDTO projectParams) {
        Project projectFromDb = projectRepository.findOne(projectParams.getId());
        BeanUtils.copyProperties(projectParams, projectFromDb);
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.delete(projectId);
    }
}
