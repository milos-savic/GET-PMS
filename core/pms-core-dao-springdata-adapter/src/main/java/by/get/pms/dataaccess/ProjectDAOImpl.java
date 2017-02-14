package by.get.pms.dataaccess;

import by.get.pms.data.ProjectData;
import by.get.pms.data.UserData;
import by.get.pms.model.Project;
import by.get.pms.model.User;
import by.get.pms.springdata.ProjectRepository;
import by.get.pms.springdata.UserRepository;
import by.get.pms.utility.Transformers;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
@Repository
class ProjectDAOImpl implements ProjectDAO {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean exists(Long projectId) {
        return projectRepository.exists(projectId);
    }

    @Override
    public Integer projectExistsByCode(String projectCode) {
        return projectRepository.projectExistsByCode(projectCode);
    }

    @Override
    public ProjectData findOne(Long projectId) {
        Project project = projectRepository.findOne(projectId);
        return Transformers.PROJECT_ENTITY_2_PROJECT_DATA_TRANSFORMER.apply(project);
    }

    @Override
    public List<ProjectData> findAll() {
        List<Project> projects = Lists.newArrayList(projectRepository.findAll());
        return projects.parallelStream()
                .map(project -> Transformers.PROJECT_ENTITY_2_PROJECT_DATA_TRANSFORMER.apply(project))
                .collect(Collectors.toList());
    }

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
    public ProjectData create(ProjectData projectParams) {
        Project newProject = new Project();
        BeanUtils.copyProperties(projectParams, newProject);

        User pmUser = userRepository.findOne(projectParams.getProjectManager().getId());
        newProject.setProjectManager(pmUser);

        newProject = projectRepository.save(newProject);

        return Transformers.PROJECT_ENTITY_2_PROJECT_DATA_TRANSFORMER.apply(newProject);
    }

    @Override
    public void update(ProjectData projectParams) {
        Project projectFromDb = projectRepository.findOne(projectParams.getId());
        BeanUtils.copyProperties(projectParams, projectFromDb);
        projectFromDb.setProjectManager(userRepository.findOne(projectParams.getProjectManager().getId()));
    }

    @Override
    public void delete(Long projectId) {
        projectRepository.delete(projectId);
    }

}
