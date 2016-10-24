package by.get.pms.dto;

import by.get.pms.model.TaskStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by milos on 19-Oct-16.
 */
public class TaskUpdateParamsForDev extends DTO {

	@NotNull
	private TaskStatus taskStatus;

	@Min(0)
	@Max(100)
	private int progress;

	@NotNull
	@Size(min = 1, max = 255)
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
