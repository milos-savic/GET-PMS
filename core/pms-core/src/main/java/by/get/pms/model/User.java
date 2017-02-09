package by.get.pms.model;

import by.get.pms.dto.UserRole;

import javax.persistence.*;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Entity
@Table(name = "user")
public class User extends PersistentEntity {

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false)
	private String email;

	@OneToOne(optional = false, cascade = CascadeType.ALL,
			mappedBy = "user", targetEntity = UserAccount.class)
	private UserAccount userAccount;

	public User() {
		super();
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public boolean isAdmin() {
		return UserRole.ROLE_ADMIN.equals(userAccount.getRole());
	}

	public boolean isProjectManager() {
		return UserRole.ROLE_PROJECT_MANAGER.equals(userAccount.getRole());
	}

	public boolean isDelveloper() {
		return UserRole.ROLE_DEV.equals(userAccount.getRole());
	}

	@Override
	public String toString() {
		return "User{" +
				"id= " + getId() + '\'' +
				"firstName= '" + firstName + '\'' +
				", lastName= '" + lastName + '\'' +
				", email= '" + email + '\'' +
				'}';
	}
}
