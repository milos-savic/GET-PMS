package by.get.pms.dataaccess;

import by.get.pms.data.ProjectData;
import by.get.pms.data.TaskData;
import by.get.pms.data.UserData;

import java.util.List;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public interface TaskDAO {

	boolean exists(Long taskId);

	boolean taskExistsByProjectAndName(ProjectData project, String name);

	List<TaskData> findAll();

	TaskData findOne(Long taskId);

	TaskData findTaskByProjectAndName(ProjectData projectData, String name);

	List<TaskData> findTasksByAssignee(UserData assignee);

	List<TaskData> findTasksByProject(ProjectData project);

	TaskData create(TaskData taskParams);

	void update(TaskData taskParams);

	void delete(Long taskId);
}
