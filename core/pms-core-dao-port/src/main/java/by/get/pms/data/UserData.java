package by.get.pms.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public class UserData extends Data {

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
	private String userName;

	private LocalDateTime creationDate;

	private Boolean active;

	@NotNull
	private UserRole role;

	public UserData() {
	}

	public UserData(long id, String firstName, String lastName, String email, String userName,
			LocalDateTime creationDate, Boolean active, UserRole role) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userName = userName;
		this.creationDate = creationDate;
		this.active = active;
		this.role = role;
	}

	@Override
	public String getBusinessIdentifier() {
		return userName;
	}

	public boolean isProjectManager() {
		return UserRole.ROLE_PROJECT_MANAGER.equals(role);
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
