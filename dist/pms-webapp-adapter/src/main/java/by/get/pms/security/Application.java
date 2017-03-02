package by.get.pms.security;

import by.get.pms.dtos.UserDTO;
import by.get.pms.dtos.UserRole;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Entity containing application session attributes.
 */
public class Application {
	private ApplicationAttributes attributes;

	private LocalDateTime created;
	private UserRole currentRole;
	private UserDTO user;

	private Application(ApplicationAttributes attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException("ApplicationAttributes must be specified");
		}
		this.attributes = attributes;
		this.created = getAttribute("created");
		this.user = getAttribute("user");
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

	public LocalDateTime getCreated() {
		return created;
	}

	public UserDTO getUser() {
		return user;
	}

	public UserRole getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(UserRole currentRole) {
		this.currentRole = currentRole;
		setAttribute("currentRole", currentRole);
	}

	public Locale getLocale() {
		return Locale.ENGLISH;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
		setAttribute("created", created);
	}

	public void setUser(UserDTO user) {
		this.user = user;
		setAttribute("user", user);
	}

	public void setCredentials(UserDTO user, UserRole currentRole) {
		clearState();
		setUser(user);
		setCurrentRole(currentRole);
		setCreated(LocalDateTime.now());
	}

	protected void clearState() {
		attributes.clearAll();
	}
}
