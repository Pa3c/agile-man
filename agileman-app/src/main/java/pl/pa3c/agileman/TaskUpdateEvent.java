package pl.pa3c.agileman;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.model.task.Task;

@AllArgsConstructor
@Data
public class TaskUpdateEvent {
	private Task newState;
	private Task oldState;
}
