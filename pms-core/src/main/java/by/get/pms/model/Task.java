package by.get.pms.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by milos on 14-Oct-16.
 */

@Entity
@Table(name = "task")
public class Task extends PersistentEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "project_status")
    private ProjectStatus projectStatus = ProjectStatus.NEW;

    @Column(name = "progress")
    private int progress;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "assignee")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "project", nullable = false)
    private Project project;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
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

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id= " + getId() + '\'' +
                "name= '" + name + '\'' +
                ", projectStatus= " + projectStatus +
                ", progress= " + progress +
                ", deadline= " + deadline +
                ", description= '" + description + '\'' +
                ", assignee= " + assignee +
                ", project= " + project +
                '}';
    }
}
