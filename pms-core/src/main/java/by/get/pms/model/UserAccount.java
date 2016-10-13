package by.get.pms.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Milos.Savic on 10/12/2016.
 */

@Entity
@Table(name = "useraccount")
public class UserAccount extends PersistentEntity {

	@OneToOne
	@JoinColumn(name = "user", unique = true, nullable = false)
	private User user;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "creationdate", nullable = false)
	private Date creationDate;

	@Column(name = "active", nullable = false)
	private Character active;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Role.class)
	@JoinTable(name = "userrole",
			joinColumns = @JoinColumn(name = "useraccount"),
			inverseJoinColumns = @JoinColumn(name = "role"))
	private Set<Role> roles = new HashSet<>();

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date startDate) {
		this.creationDate = startDate;
	}

	public Character getActive() {
		return active;
	}

	public void setActive(Character enabled) {
		this.active = enabled;
	}
}
