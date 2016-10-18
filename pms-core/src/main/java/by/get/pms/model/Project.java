package by.get.pms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by milos on 14-Oct-16.
 */

@Entity
@Table(name = "project")
public class Project extends PersistentEntity {

	@Column(name = "code", unique = true, nullable = false, length = 30)
	private String code;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne(optional = false)
	@JoinColumn(name = "project_manager")
	private User projectManager;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	private List<Task> tasks;

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

	public User getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(User projectManager) {
		this.projectManager = projectManager;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public Serializable getIdentifier() {
		return getCode();
	}

	@Override
	public String toString() {
		return "Project{" +
				"id='" + getId() + '\'' +
				"code='" + code + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", projectManager=" + projectManager +
				'}';
	}
}
