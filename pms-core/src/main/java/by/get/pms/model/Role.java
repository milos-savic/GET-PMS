package by.get.pms.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Entity
@Table(name = "role")
public class Role extends PersistentEntity {

	@Column(name = "name_", nullable = false)
	private String name;

	@Column(name = "description_")
	private String description;

	@ManyToOne
	@JoinColumn(name = "fk_roletype", nullable = false)
	private RoleType roleType;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = UserAccount.class)
	@JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "fk_role"), inverseJoinColumns = @JoinColumn(name = "fk_useraccount"))
	private List<UserAccount> userAccounts = new ArrayList<UserAccount>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public List<UserAccount> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
	}
}
