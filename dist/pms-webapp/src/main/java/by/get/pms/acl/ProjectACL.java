package by.get.pms.acl;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.model.UserRole;
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

/**
 * Created by Milos.Savic on 2/7/2017.
 */
@Component
public class ProjectACL {

	@Autowired
	private MutableAclService mutableAclService;

	@Autowired
	private ACLUtil aclUtil;

	@Transactional
	public void createProjectACL(ProjectDTO projectDtoNew) {
		ObjectIdentity oid = new ObjectIdentityImpl(ProjectDTO.class, projectDtoNew.getId());

		aclUtil.addPermission(oid, new GrantedAuthoritySid(UserRole.ROLE_ADMIN.name()), BasePermission.ADMINISTRATION);
		aclUtil.addPermission(oid, new PrincipalSid(projectDtoNew.getProjectManager().getUserName()),
				BasePermission.ADMINISTRATION);
	}

	@Transactional
	public void updateProjectACL(ProjectDTO newProject, ProjectDTO projectFromDb) {
		ObjectIdentity oid = new ObjectIdentityImpl(ProjectDTO.class, newProject.getId());

		Sid oldPMSid = new PrincipalSid(projectFromDb.getProjectManager().getUserName());
		Sid newPMSid = new PrincipalSid(newProject.getProjectManager().getUserName());
		aclUtil.deletePermission(oid, oldPMSid, BasePermission.ADMINISTRATION);
		aclUtil.addPermission(oid, newPMSid, BasePermission.ADMINISTRATION);
	}

	public void deleteACL(Long projectId) {
		ObjectIdentity oid = new ObjectIdentityImpl(ProjectDTO.class, projectId);
		mutableAclService.deleteAcl(oid, false);
	}
}
