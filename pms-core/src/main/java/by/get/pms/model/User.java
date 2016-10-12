package by.get.pms.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Entity
@Table(name = "user")
public class User extends PersistentEntity {

	@Column(name = "firstname_", nullable = false)
	private String firstName;

	@Column(name = "middlename_")
	private String middleName;

	@Column(name = "lastname_", nullable = false)
	private String lastName;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "user")
	private List<UserAccount> userAccounts = new ArrayList<>();

	public List<UserAccount> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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

	public String toString() {
		return "[User id=" + this.getId() + "]";
	}
}
