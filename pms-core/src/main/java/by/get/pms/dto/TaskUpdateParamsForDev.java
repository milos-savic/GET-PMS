package by.get.pms.dto;

import by.get.pms.model.TaskStatus;

import java.io.Serializable;

/**
 * Created by milos on 19-Oct-16.
 */
public class TaskUpdateParamsForDev extends DTO {

    private TaskStatus taskStatus;
    private int progress;
    private String description;

    public TaskUpdateParamsForDev() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
