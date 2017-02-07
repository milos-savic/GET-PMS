package by.get.pms.acl;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.dto.TaskDTO;
import by.get.pms.dto.UserDTO;
import by.get.pms.model.UserRole;
import by.get.pms.service.user.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Milos.Savic on 2/7/2017.
 */
@Component
public class TaskACL {

	@Autowired
	private ACLUtil aclUtil;

	@Autowired
	private UserFacade userFacade;

	@Transactional
	public void createACL(TaskDTO taskDtoNew) {
		ObjectIdentity oidTask = new ObjectIdentityImpl(TaskDTO.class, taskDtoNew.getId());
		ObjectIdentity oidProject = new ObjectIdentityImpl(ProjectDTO.class, taskDtoNew.getProject().getId());

		aclUtil.addPermission(oidTask, new GrantedAuthoritySid(UserRole.ROLE_ADMIN.name()),
				BasePermission.ADMINISTRATION);

		String pmUsername = taskDtoNew.getProject().getProjectManager().getUserName();
		aclUtil.addPermission(oidTask, new PrincipalSid(pmUsername), BasePermission.ADMINISTRATION);

		if (taskDtoNew.getAssignee() != null) {
			String assigneeUsername = taskDtoNew.getAssignee().getUserName();
			aclUtil.addPermission(oidTask, new PrincipalSid(assigneeUsername), BasePermission.ADMINISTRATION);

			aclUtil.addPermission(oidProject, new PrincipalSid(assigneeUsername), BasePermission.READ);
		} else {
			List<UserDTO> allUsers = userFacade.getAllUsers();
			allUsers.forEach(user -> {
				aclUtil.addPermission(oidTask, new PrincipalSid(user.getUserName()), BasePermission.READ);
				aclUtil.addPermission(oidProject, new PrincipalSid(user.getUserName()), BasePermission.READ);
			});
		}
	}
}
