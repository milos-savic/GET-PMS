package by.get.pms.acl;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.TaskDTO;
import by.get.pms.dtos.UserRole;
import by.get.pms.facade.project.ProjectFacade;
import by.get.pms.facade.task.TaskFacade;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by Milos.Savic on 2/8/2017.
 */
@Component
class ProjectACLImpl implements ProjectACL {

	@Autowired
	private MutableAclService mutableAclService;

	@Autowired
	private ACLUtil aclUtil;

	@Autowired
	private ProjectFacade projectFacade;

	@Autowired
	private TaskFacade taskFacade;

	@Override
	public List<ProjectDTO> retrieveProjectsBasedOnACL() {
		return projectFacade.getAll();
	}

	@Override
	@Transactional
	public void createProjectACL(ProjectDTO project) {
		ObjectIdentity oid = new ObjectIdentityImpl(ProjectDTO.class, project.getId());

		aclUtil.addPermission(oid, new GrantedAuthoritySid(UserRole.ROLE_ADMIN.name()), BasePermission.ADMINISTRATION);

		Sid pmSid = new PrincipalSid(project.getProjectManager().getUserName());
		aclUtil.addPermission(oid, pmSid, BasePermission.ADMINISTRATION);
		aclUtil.addPermission(oid, pmSid, BasePermission.READ);
	}

	@Override
	@Transactional
	public void updateProjectACL(ProjectDTO oldProject, ProjectDTO newProject) {

		if (oldProject.getProjectManager().equals(newProject.getProjectManager())) {
			return;
		}

		ObjectIdentity projetOid = new ObjectIdentityImpl(ProjectDTO.class, oldProject.getId());

		Sid oldPMSid = new PrincipalSid(oldProject.getProjectManager().getUserName());
		Sid newPMSid = new PrincipalSid(newProject.getProjectManager().getUserName());

		aclUtil.deletePermission(projetOid, oldPMSid, BasePermission.ADMINISTRATION);
		aclUtil.addPermission(projetOid, newPMSid, BasePermission.ADMINISTRATION);

		List<TaskDTO> projectTasks = taskFacade.getProjectTasks(oldProject);
		int countOfAssigned = 0;
		for (TaskDTO projectTask : projectTasks) {
			ObjectIdentity taskOid = new ObjectIdentityImpl(TaskDTO.class, projectTask.getId());

			if (projectTask.getAssignee() != null) {
				aclUtil.deletePermission(taskOid, oldPMSid, BasePermission.READ);
				aclUtil.deletePermission(taskOid, oldPMSid, BasePermission.ADMINISTRATION);
				countOfAssigned++;
			} else {
				aclUtil.deletePermission(taskOid, oldPMSid, BasePermission.ADMINISTRATION);
			}
		}

		if (countOfAssigned == projectTasks.size()) {
			aclUtil.deletePermission(projetOid, oldPMSid, BasePermission.READ);
		}
	}

	@Override
	public void deleteACL(Long projectId) {
		ObjectIdentity oid = new ObjectIdentityImpl(ProjectDTO.class, projectId);
		mutableAclService.deleteAcl(oid, false);
	}
}
