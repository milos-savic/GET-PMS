package by.get.pms.web.conversion;

import by.get.pms.model.TaskStatus;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by milos on 23-Oct-16.
 */
public class TaskStatusFormatter implements Formatter<TaskStatus> {
    @Override
    public TaskStatus parse(String taskStatusName, Locale locale) throws ParseException {
        return TaskStatus.make(taskStatusName);
    }

    @Override
    public String print(TaskStatus taskStatus, Locale locale) {
        return taskStatus != null ? taskStatus.name() : "";
    }
}
