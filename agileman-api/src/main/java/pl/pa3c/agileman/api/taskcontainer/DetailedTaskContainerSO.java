package pl.pa3c.agileman.api.taskcontainer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import pl.pa3c.agileman.api.state.StateSO;
import pl.pa3c.agileman.api.task.TaskSO;

@Data
public class DetailedTaskContainerSO extends TaskContainerSO {
	Long teamId;
	Long projectId;
	Set<StateSO> states;
	Map<String,List<TaskSO>> tasks;
}
