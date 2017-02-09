package by.get.pms.acl;

import by.get.pms.dtos.ProjectDTO;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

/**
 * Created by Milos.Savic on 2/7/2017.
 */
public interface ProjectACL {

	@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'administration')")
	List<ProjectDTO> retrieveProjectsBasedOnACL();

	void createProjectACL(ProjectDTO project);

	void updateProjectACL(ProjectDTO oldProject, ProjectDTO newProject);

	void deleteACL(Long projectId);
}
