package by.get.pms.service.project;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.model.Project;
import by.get.pms.service.task.TaskService;
import by.get.pms.utility.Transformers;
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
class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskService taskService;

	@Override
	public List<ProjectDTO> getAll() {
		Set<Project> projects = Sets.newHashSet(projectRepository.findAll());
		return projects.parallelStream()
				.map(Transformers.PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER::apply)
				.collect(Collectors.toList());
	}

	@Override
	public boolean projectExists(Long projectId) {
		return projectRepository.exists(projectId);
	}

	@Override
	public boolean projectExistsByCode(String projectCode) {
		return projectRepository.projectExistsByCode(projectCode) > 0;
	}

	@Override
	public ProjectDTO getProject(Long projectId) {
		return Transformers.PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER.apply(projectRepository.findOne(projectId));
	}

	@Override
	public ProjectDTO getProjectByCode(String projectCode) {
		return Transformers.PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER
				.apply(projectRepository.findProjectByCode(projectCode));
	}

	@Override
	public List<ProjectDTO> getProjectManagerProjects(UserDTO projectManager) {
		List<Project> projectManagerProjects = projectRepository
				.findProjectsByProjectManager(userRepository.findOne(projectManager.getId()));

		return projectManagerProjects.parallelStream()
				.map(project -> Transformers.PROJECT_ENTITY_2_PROJECT_DTO_TRANSFORMER.apply(project))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ProjectDTO createProject(ProjectDTO projectParams) {
		Project newProject = new Project();
		BeanUtils.copyProperties(projectParams, newProject);
		newProject.setProjectManager(userRepository.findOne(projectParams.getProjectManager().getId()));

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
	public void removeProject(Long projectId) {
		projectRepository.delete(projectId);
	}
}
