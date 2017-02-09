package by.get.pms.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by milos on 19-Oct-16.
 */
public class TaskUpdateParamsForPM extends DTO {

	@NotNull
	private TaskStatus taskStatus;

	@Min(0)
	@Max(100)
	private int progress;

	@NotNull
	@Size(min = 1, max = 255)
	private String description;

	@NotNull
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
