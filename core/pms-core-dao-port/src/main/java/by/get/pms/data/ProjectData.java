package by.get.pms.data;

import by.get.pms.validation.ProjectManager;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Milos.Savic on 2/10/2017.
 */
public class ProjectData extends Data {

	@NotNull
	@Size(min = 1, max = 30)
	private String code;

	@NotNull
	@Size(min = 1, max = 50)
	private String name;

	@NotNull
	@Size(min = 1, max = 255)
	private String description;

	@NotNull
	@ProjectManager
	private UserData projectManager;

	public ProjectData() {
	}

	public ProjectData(long id, String code, String name, String description, UserData projectManager) {
		super(id);
		this.code = code;
		this.name = name;
		this.description = description;
		this.projectManager = projectManager;
	}

	@Override
	public String getBusinessIdentifier() {
		return code;
	}

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

	public UserData getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(UserData projectManager) {
		this.projectManager = projectManager;
	}

	public boolean isMyProjectManager(UserData projectManager) {
		return this.projectManager.equals(projectManager);
	}

	@Override
	public String toString() {
		return "ProjectDTO{" + "id='" + getId() + '\'' + "code='" + code + '\'' + ", name='" + name + '\''
				+ ", description='" + description + '\'' + ", projectManager=" + projectManager + '}';
	}
}
