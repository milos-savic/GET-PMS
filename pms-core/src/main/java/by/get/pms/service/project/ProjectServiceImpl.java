package by.get.pms.service.project;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.model.Project;
import by.get.pms.utility.Transformers;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
	public List<ProjectDTO> getProjectsAvailableForDeveloper(UserDTO developer) {
		return null;
	}

	@Override
	public UserDTO createProject(ProjectDTO projectDTO) {
		return null;
	}

	@Override
	public void updateProject(ProjectDTO projectDTO) {

	}

	@Override
	public void deleteProject(Long projectId) {

	}
}
