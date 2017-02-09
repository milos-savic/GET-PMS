package by.get.pms.security;

import by.get.pms.dtos.ProjectDTO;

import by.get.pms.dtos.TaskDTO;
import by.get.pms.dtos.UserRole;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

/**
 * Created by milos on 05-Feb-17.
 */

/**
 * Depends on projects_and_tasks.sql
 */
public class AclInitializer implements InitializingBean {

	private TransactionTemplate tt;
	private MutableAclService mutableAclService;

	public AclInitializer(PlatformTransactionManager transactionManager, MutableAclService mutableAclService) {
		Objects.requireNonNull(transactionManager, "transactionManager required");
		Objects.requireNonNull(mutableAclService, "mutableAclService required");

		tt = new TransactionTemplate(transactionManager);
		this.mutableAclService = mutableAclService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		// Set a user account that will initially own all the created data
		Authentication authRequest = new UsernamePasswordAuthenticationToken("bond", "007",
				AuthorityUtils.createAuthorityList("ROLE_IGNORED"));
		SecurityContextHolder.getContext().setAuthentication(authRequest);

		long[] projectIds = { 1L, 2L, 3L };

		Map<Long, String> projectIdsToAssignedPMUsernames = new HashMap<>();
		projectIdsToAssignedPMUsernames.put(1L, "born");
		projectIdsToAssignedPMUsernames.put(2L, "batman");
		projectIdsToAssignedPMUsernames.put(3L, "spiderman");

		Map<Long, List<String>> projectIdsToAllocatedUsers = new HashMap<>();
		projectIdsToAllocatedUsers.put(1L, Arrays.asList("captain_america", "robin"));
		projectIdsToAllocatedUsers.put(2L, Arrays.asList("born", "superman", "spiderman"));
		projectIdsToAllocatedUsers.put(3L, Arrays.asList("born", "superman", "spiderman"));

		createProjectACLs(projectIds);
		populateProjectACLs(projectIdsToAssignedPMUsernames, projectIdsToAllocatedUsers);

		long[] taskIds = { 10L, 11L, 12L, 20L, 21L, 22L, 30L, 31L, 32L };
		Map<Long, String> taskIdsToAssignedUsernames = new HashMap<>();
		taskIdsToAssignedUsernames.put(10L, "captain_america");
		taskIdsToAssignedUsernames.put(11L, "robin");
		taskIdsToAssignedUsernames.put(12L, "robin");
		taskIdsToAssignedUsernames.put(20L, "superman");
		taskIdsToAssignedUsernames.put(21L, "born");
		taskIdsToAssignedUsernames.put(22L, "spiderman");
		taskIdsToAssignedUsernames.put(30L, "superman");
		taskIdsToAssignedUsernames.put(31L, "born");
		taskIdsToAssignedUsernames.put(32L, "spiderman");

		createTaskACLs(taskIds);
		populateTaskACLs(taskIdsToAssignedUsernames);

		SecurityContextHolder.clearContext();
	}

	private void createProjectACLs(long[] projectIds) {
		for (long projectId : projectIds) {
			final ObjectIdentity projectObjectIdentity = new ObjectIdentityImpl(ProjectDTO.class, projectId);

			tt.execute((TransactionStatus arg0) -> {
				mutableAclService.createAcl(projectObjectIdentity);
				return null;
			});
		}
	}

	private void populateProjectACLs(Map<Long, String> projectIdToAssignedPMUsername,
			Map<Long, List<String>> projectIdsToAllocatedUsers) {
		populateProjectACLsForAdmin(projectIdToAssignedPMUsername.keySet());
		populateProjectACLsForPMs(projectIdToAssignedPMUsername);
		populateProjectACLsForUsers(projectIdsToAllocatedUsers);
	}

	private void populateProjectACLsForAdmin(Set<Long> projectIds) {
		projectIds.forEach(projectId -> {
			final ObjectIdentity projectObjectIdentity = new ObjectIdentityImpl(ProjectDTO.class, projectId);
			grantPermissionToObjectIdentityForRole(projectObjectIdentity, UserRole.ROLE_ADMIN,
					BasePermission.ADMINISTRATION);
		});
	}

	private void grantPermissionToObjectIdentityForRole(ObjectIdentity objectIdentity, UserRole userRole,
			Permission permission) {
		AclImpl acl = (AclImpl) mutableAclService.readAclById(objectIdentity);
		acl.insertAce(acl.getEntries().size(), permission, new GrantedAuthoritySid(userRole.name()), true);
		updateAclInTransaction(acl);
	}

	private void updateAclInTransaction(final MutableAcl acl) {
		tt.execute((TransactionStatus arg0) -> {
			mutableAclService.updateAcl(acl);
			return null;
		});
	}

	private void populateProjectACLsForPMs(Map<Long, String> projectIdsToAssignedPMUsernames) {
		for (Map.Entry<Long, String> entry : projectIdsToAssignedPMUsernames.entrySet()) {
			final ObjectIdentity projectObjectIdentity = new ObjectIdentityImpl(ProjectDTO.class, entry.getKey());
			grantPermissionToObjectIdentityForPrincipal(projectObjectIdentity, entry.getValue(),
					BasePermission.ADMINISTRATION);
		}
	}

	private void grantPermissionToObjectIdentityForPrincipal(ObjectIdentity objectIdentity, String recipientUsername,
			Permission permission) {
		AclImpl acl = (AclImpl) mutableAclService.readAclById(objectIdentity);
		acl.insertAce(acl.getEntries().size(), permission, new PrincipalSid(recipientUsername), true);
		updateAclInTransaction(acl);
	}

	private void populateProjectACLsForUsers(Map<Long, List<String>> projectIdsToAllocatedUsers) {
		for (Map.Entry<Long, List<String>> longListEntry : projectIdsToAllocatedUsers.entrySet()) {
			final ObjectIdentity projectObjectIdentity = new ObjectIdentityImpl(ProjectDTO.class,
					longListEntry.getKey());
			for (String recipientUsername : longListEntry.getValue()) {
				grantPermissionToObjectIdentityForPrincipal(projectObjectIdentity, recipientUsername,
						BasePermission.READ);
			}
		}
	}

	private void createTaskACLs(long[] taskIds) {
		for (long taskId : taskIds) {
			final ObjectIdentity taskObjectIdentity = new ObjectIdentityImpl(TaskDTO.class, taskId);

			tt.execute((TransactionStatus arg0) -> {
				mutableAclService.createAcl(taskObjectIdentity);
				return null;
			});
		}
	}

	private void populateTaskACLs(Map<Long, String> taskIdsToAssignedUsernames) {
		populateTaskACLsForAdmin(taskIdsToAssignedUsernames.keySet());
		populateTaskACLsForUsers(taskIdsToAssignedUsernames);
	}

	private void populateTaskACLsForAdmin(Set<Long> taskIds) {
		taskIds.forEach(taskId -> {
			final ObjectIdentity taskObjectIdentity = new ObjectIdentityImpl(TaskDTO.class, taskId);
			grantPermissionToObjectIdentityForRole(taskObjectIdentity, UserRole.ROLE_ADMIN,
					BasePermission.ADMINISTRATION);
		});
	}

	private void populateTaskACLsForUsers(Map<Long, String> taskIdsToAssignedUsernames) {
		for (Map.Entry<Long, String> longStringEntry : taskIdsToAssignedUsernames.entrySet()) {
			final ObjectIdentity taskObjectIdentity = new ObjectIdentityImpl(TaskDTO.class, longStringEntry.getKey());
			grantPermissionToObjectIdentityForPrincipal(taskObjectIdentity, longStringEntry.getValue(),
					BasePermission.ADMINISTRATION);
		}
	}

}
