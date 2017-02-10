package by.get.pms.dataaccess;

import by.get.pms.data.ProjectData;
import by.get.pms.data.UserData;

import java.util.List;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface ProjectDAO {
	ProjectData findProjectByCode(String projectCode);

	List<ProjectData> findProjectsByProjectManager(UserData projectManager);

	Integer projectExistsByCode(String projectCode);
}
