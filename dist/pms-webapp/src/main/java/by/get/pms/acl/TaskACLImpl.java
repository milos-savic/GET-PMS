package by.get.pms.acl;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.TaskDTO;
import by.get.pms.dtos.UserDTO;
import by.get.pms.dtos.UserRole;
import by.get.pms.facade.task.TaskFacade;
import by.get.pms.facade.user.UserFacade;
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
 * Created by Milos.Savic on 2/8/2017.
 */
@Component
class TaskACLImpl implements TaskACL {

	@Autowired
	private ACLUtil aclUtil;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private MutableAclService mutableAclService;

	@Autowired
	private TaskFacade taskFacade;

	@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'administration')")
	public List<TaskDTO> retrieveProjectTasksBasedOnACL(ProjectDTO project) {
		return taskFacade.getProjectTasks(project);
	}

	@Transactional
	public void createACL(TaskDTO task) {
		ObjectIdentity oidTask = new ObjectIdentityImpl(TaskDTO.class, task.getId());
		ObjectIdentity oidProject = new ObjectIdentityImpl(ProjectDTO.class, task.getProject().getId());

		aclUtil.addPermission(oidTask, new GrantedAuthoritySid(UserRole.ROLE_ADMIN.name()),
				BasePermission.ADMINISTRATION);

		Sid pmSid = new PrincipalSid(task.getProject().getProjectManager().getUserName());
		aclUtil.addPermission(oidTask, pmSid, BasePermission.ADMINISTRATION);
		aclUtil.addPermission(oidTask, pmSid, BasePermission.READ);

		if (task.getAssignee() != null) {
			Sid assigneeSid = new PrincipalSid(task.getAssignee().getUserName());
			aclUtil.addPermission(oidTask, assigneeSid, BasePermission.ADMINISTRATION);
			aclUtil.addPermission(oidTask, assigneeSid, BasePermission.READ);

			aclUtil.addPermission(oidProject, assigneeSid, BasePermission.READ);
		} else {
			List<UserDTO> allUsers = userFacade.getAllUsers();
			allUsers.forEach(user -> {
				Sid userSid = new PrincipalSid(user.getUserName());
				aclUtil.addPermission(oidTask, userSid, BasePermission.READ);
				aclUtil.addPermission(oidProject, userSid, BasePermission.READ);
			});
		}
	}

	@Transactional
	public void updateACL(TaskDTO oldTask, TaskDTO newTask) {
		if (!assigneeChanged(oldTask, newTask)) {
			return;
		}

		deleteACL(newTask.getId());
		createACL(newTask);
	}

	private boolean assigneeChanged(TaskDTO oldTask, TaskDTO newTask) {
		UserDTO oldAssignee = oldTask.getAssignee();
		UserDTO newAssignee = newTask.getAssignee();

		if (oldAssignee == null && newAssignee == null) {
			return false;
		}

		if (oldAssignee != null && newAssignee != null) {
			return oldAssignee.equals(newAssignee);
		}

		return true;
	}

	public void deleteACL(Long taskId) {
		ObjectIdentity oid = new ObjectIdentityImpl(TaskDTO.class, taskId);
		mutableAclService.deleteAcl(oid, false);
	}
}
