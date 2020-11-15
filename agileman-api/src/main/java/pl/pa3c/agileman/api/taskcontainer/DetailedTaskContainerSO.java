package pl.pa3c.agileman.api.taskcontainer;

import java.util.List;
import java.util.Set;

import lombok.Data;
import pl.pa3c.agileman.api.task.TaskSO;

@Data
public class DetailedTaskContainerSO extends TaskContainerSO {
	Set<StateSO> states;
	List<TaskSO> tasks;
}
