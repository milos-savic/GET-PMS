package by.get.pms.model;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Entity
@Table(name = "role")
public class Role extends PersistentEntity {

	@Column(name = "code", unique = true, nullable = false, length = 30)
	private String code;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne(optional = false)
	@JoinColumn(name = "roletype")
	private RoleType roleType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private List<UserAccount> userAccounts = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

    @Override
    public Serializable getIdentifier() {
        return getCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id= " + getId() + '\'' +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", roleType=" + roleType +
                ", userAccounts=" + userAccounts +
                '}';
    }
}
