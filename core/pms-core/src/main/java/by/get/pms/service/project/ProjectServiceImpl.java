package by.get.pms.service.project;

import by.get.pms.data.ProjectData;
import by.get.pms.dataaccess.ProjectDAO;
import by.get.pms.dataaccess.UserDAO;
import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.UserDTO;
import by.get.pms.service.task.TaskService;
import by.get.pms.utility.Data2DtoTransformers;
import by.get.pms.utility.Dto2DataTransformers;
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
class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TaskService taskService;

    @Override
    public List<ProjectDTO> getAll() {
        List<ProjectData> projects = projectDAO.findAll();
        return projects.parallelStream()
                .map(Data2DtoTransformers.PROJECT_DATA_2_PROJECT_DTO_TRANSFORMER::apply)
                .collect(Collectors.toList());
    }

    @Override
    public boolean projectExists(Long projectId) {
        return projectDAO.exists(projectId);
    }

    @Override
    public boolean projectExistsByCode(String projectCode) {
        return projectDAO.projectExistsByCode(projectCode) > 0;
    }

    @Override
    public ProjectDTO getProject(Long projectId) {
        return Data2DtoTransformers.PROJECT_DATA_2_PROJECT_DTO_TRANSFORMER.apply(projectDAO.findOne(projectId));
    }

    @Override
    public ProjectDTO getProjectByCode(String projectCode) {
        return Data2DtoTransformers.PROJECT_DATA_2_PROJECT_DTO_TRANSFORMER
                .apply(projectDAO.findProjectByCode(projectCode));
    }

    @Override
    public List<ProjectDTO> getProjectManagerProjects(UserDTO projectManager) {
        List<ProjectData> projectManagerProjectDatas = projectDAO
                .findProjectsByProjectManager(userDAO.findOne(projectManager.getId()));

        return projectManagerProjectDatas.parallelStream()
                .map(projectData -> Data2DtoTransformers.PROJECT_DATA_2_PROJECT_DTO_TRANSFORMER.apply(projectData))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectDTO createProject(ProjectDTO projectParams) {
        ProjectData projectData = projectDAO.create(Dto2DataTransformers.PROJECT_DTO_2_PROJECT_DATA_TRANSFORMER.apply(projectParams));
        return Data2DtoTransformers.PROJECT_DATA_2_PROJECT_DTO_TRANSFORMER.apply(projectData);
    }

    @Override
    @Transactional
    public void updateProject(ProjectDTO projectParams) {
        projectDAO.update(Dto2DataTransformers.PROJECT_DTO_2_PROJECT_DATA_TRANSFORMER.apply(projectParams));
    }

    @Override
    @Transactional
    public void removeProject(Long projectId) {
        projectDAO.delete(projectId);
    }
}
