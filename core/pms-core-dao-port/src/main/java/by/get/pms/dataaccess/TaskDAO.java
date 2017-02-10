package by.get.pms.dataaccess;

import by.get.pms.data.ProjectData;
import by.get.pms.data.TaskData;
import by.get.pms.data.UserData;

import java.util.List;
import java.util.Set;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface TaskDAO {

	TaskData findTaskByProjectAndName(ProjectData project, String name);

	List<TaskData> findTasksByAssignee(UserData assignee);

	List<TaskData> findTasksByProject(ProjectData project);

	List<TaskData> findTasksByIds(Set<Long> tids);

	Integer taskExistsByProjectAndName(ProjectData project, String name);
}
