package by.get.pms.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Milos.Savic on 2/7/2017.
 */
@Component
class ACLUtil {

	@Autowired
	private MutableAclService mutableAclService;

	void addPermission(ObjectIdentity oid, Sid recipient, Permission permission) {
		MutableAcl acl;

		try {
			acl = (MutableAcl) mutableAclService.readAclById(oid);
		} catch (NotFoundException nfe) {
			acl = mutableAclService.createAcl(oid);
		}

		acl.insertAce(acl.getEntries().size(), permission, recipient, true);
		mutableAclService.updateAcl(acl);
	}

	void deletePermission(ObjectIdentity oid, Sid recipient, Permission permission) {
		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

		List<AccessControlEntry> entries = acl.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission)) {
				acl.deleteAce(i);
			}
		}
		mutableAclService.updateAcl(acl);
	}

}
