package by.get.pms.dto;

import by.get.pms.validation.ProjectManager;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Milos.Savic on 10/17/2016.
 */
public class ProjectDTO extends DTO {

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
    private UserDTO projectManager;

    public ProjectDTO() {
    }

    public ProjectDTO(long id, String code, String name, String description, UserDTO projectManager) {
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

    public UserDTO getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(UserDTO projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "id='" + getId() + '\'' +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectManager=" + projectManager +
                '}';
    }
}
