package by.get.pms.dataaccess;

import by.get.pms.data.ProjectData;
import by.get.pms.data.UserData;

import java.util.List;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface ProjectDAO {

	boolean exists(Long projectId);

	Integer projectExistsByCode(String projectCode);

	ProjectData findOne(Long projectId);

	List<ProjectData> findAll();

	ProjectData findProjectByCode(String projectCode);

	List<ProjectData> findProjectsByProjectManager(UserData projectManager);

	ProjectData createProject(ProjectData projectParams);
}
