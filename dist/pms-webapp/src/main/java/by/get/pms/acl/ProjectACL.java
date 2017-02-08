package by.get.pms.acl;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.model.UserRole;
import by.get.pms.service.project.ProjectFacade;
import by.get.pms.service.task.TaskFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
