package by.get.pms.security;

import by.get.pms.model.*;
import by.get.pms.model.User;

import java.util.Date;
import java.util.Locale;

/**
 *
 * Entity containing application session attributes.
 */
public class Application {
	private ApplicationAttributes attributes;

	private Date created;
	private UserAccount userAccount;
	private Role currentRole;
	private by.get.pms.model.User user;

	public Application(ApplicationAttributes attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException("ApplicationAttributes must be specified");
		}
		this.attributes = attributes;
		this.created = getAttribute("created");
		this.userAccount = getAttribute("userAccount");
		if (userAccount != null) {
			this.user = userAccount.getUser();
		}
		this.currentRole = getAttribute("currentRole");
	}

	public <T> T getAttribute(String key) {
		ApplicationAttributes applicationAttributes = getApplicationAttributes();
		return (T) applicationAttributes.get(key);
	}

	public <T> void setAttribute(String key, T value) {
		ApplicationAttributes applicationAttributes = getApplicationAttributes();
		applicationAttributes.put(key, value);
	}

	protected ApplicationAttributes getApplicationAttributes() {
		return attributes;
	}

	public static Application getInstance() {
		return new Application(ApplicationSecurityContext.getContext());
	}

	public Date getCreated() {
		return created;
	}

	public by.get.pms.model.User getUser() {
		return user;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public Role getCurrentRole() {
		return currentRole;
	}

	public Long getRoleId() {
		return currentRole != null ? currentRole.getId() : -1L;
	}

	public void setCurrentRole(Role currentRole) {
		this.currentRole = currentRole;
		setAttribute("currentRole", currentRole);
	}


	public Locale getLocale() {
		return Locale.ENGLISH;
	}

	public void setCreated(Date created) {
		this.created = created;
		setAttribute("created", created);
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
		if (userAccount != null) {
			this.user = userAccount.getUser();
		} else {
			user = null;
		}
		setAttribute("userAccount", userAccount);
	}

	public void setCredentials(UserAccount userAccount, Role currentRole) {
		clearState();
		setUserAccount(userAccount);
		setCurrentRole(currentRole);
		setCreated(new Date());
	}

	protected void clearState() {
		attributes.clearAll();
	}

}
