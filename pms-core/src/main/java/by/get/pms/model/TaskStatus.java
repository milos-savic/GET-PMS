package by.get.pms.model;

/**
 * Created by milos on 14-Oct-16.
 */
public enum TaskStatus {
    NEW, IN_PROGRESS, FINISHED;

    public static TaskStatus make(String taskStatusName) {
        for (TaskStatus taskStatus : values()) {
            if (taskStatus.name().equalsIgnoreCase(taskStatusName)) {
                return taskStatus;
            }
        }
        throw new IllegalArgumentException(String.format("Task status name: %s isn't in predefined set of task statuses!", taskStatusName));
    }
}
