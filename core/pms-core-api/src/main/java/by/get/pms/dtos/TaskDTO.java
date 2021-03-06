package by.get.pms.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by Milos.Savic on 10/17/2016.
 */
public class TaskDTO extends DTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private TaskStatus taskStatus;

    @Min(0)
    @Max(100)
    private int progress;

    @NotNull
    private LocalDate deadline;

    @NotNull
    @Size(min = 1, max = 255)
    private String description;

    private UserDTO assignee;

    @NotNull
    private ProjectDTO project;

    public TaskDTO() {
    }

    public TaskDTO(Long id, String name, TaskStatus taskStatus, int progress, LocalDate deadline, String description,
                   UserDTO assignee, ProjectDTO project) {
        super(id);
        this.name = name;
        this.taskStatus = taskStatus;
        this.progress = progress;
        this.deadline = deadline;
        this.description = description;
        this.assignee = assignee;
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDTO assignee) {
        this.assignee = assignee;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", taskStatus=" + taskStatus +
                ", progress=" + progress +
                ", deadline=" + deadline +
                ", description='" + description + '\'' +
                ", assignee=" + assignee +
                ", project=" + project +
                '}';
    }
}
