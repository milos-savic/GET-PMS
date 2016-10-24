package by.get.pms.dto;

import by.get.pms.model.UserRole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by Milos.Savic on 10/17/2016.
 */
public class UserDTO extends DTO {

	@NotNull
	@Size(min = 1, max = 255)
	private String firstName;

	@NotNull
	@Size(min = 1, max = 255)
	private String lastName;

	@NotNull
	@Size(min = 1, max = 255)
	private String email;

	@NotNull
	@Size(min = 1, max = 30)
	private String username;

	@NotNull
	private LocalDateTime creationDate;

	private Boolean active;

	@NotNull
	private UserRole role;

	public UserDTO() {
	}

	public UserDTO(long id, String firstName, String lastName, String email, String username,
			LocalDateTime creationDate, Boolean active, UserRole role) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.creationDate = creationDate;
		this.active = active;
		this.role = role;
	}

	@Override
	public String getBusinessIdentifier() {
		return username;
	}

	public boolean isAdmin() {
		return UserRole.ADMIN.equals(role);
	}

	public boolean isProjectManager() {
		return UserRole.PROJECT_MANAGER.equals(role);
	}

	public boolean isDeveloper() {
		return UserRole.DEV.equals(role);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
