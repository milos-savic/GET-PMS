package by.get.pms.dataaccess;

import by.get.pms.data.ProjectData;
import by.get.pms.data.UserData;
import by.get.pms.model.Project;
import by.get.pms.model.User;
import by.get.pms.springdata.ProjectRepository;
import by.get.pms.springdata.UserRepository;
import by.get.pms.utility.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
class ProjectDAOImpl implements ProjectDAO {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ProjectData findProjectByCode(String projectCode) {
		Project project = projectRepository.findProjectByCode(projectCode);
		return Transformers.PROJECT_ENTITY_2_PROJECT_DATA_TRANSFORMER.apply(project);
	}

	@Override
	public List<ProjectData> findProjectsByProjectManager(UserData projectManager) {
		User pm = userRepository.findOne(projectManager.getId());
		List<Project> projectManagerProjects = projectRepository.findProjectsByProjectManager(pm);
		return projectManagerProjects.parallelStream()
				.map(project -> Transformers.PROJECT_ENTITY_2_PROJECT_DATA_TRANSFORMER.apply(project))
				.collect(Collectors.toList());
	}

	@Override
	public Integer projectExistsByCode(String projectCode) {
		return projectRepository.projectExistsByCode(projectCode);
	}
}
