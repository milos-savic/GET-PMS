package by.get.pms.dto;

import by.get.pms.dto.UserDTO;
import by.get.pms.model.TaskStatus;

import java.time.LocalDate;

/**
 * Created by milos on 19-Oct-16.
 */
public class TaskUpdateParamsForPM extends DTO {

    private TaskStatus taskStatus;
    private int progress;
    private String description;
    private LocalDate deadline;
    private UserDTO assigneeDTO;

    public TaskUpdateParamsForPM() {
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

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public UserDTO getAssigneeDTO() {
        return assigneeDTO;
    }

    public void setAssigneeDTO(UserDTO assigneeDTO) {
        this.assigneeDTO = assigneeDTO;
    }
}
