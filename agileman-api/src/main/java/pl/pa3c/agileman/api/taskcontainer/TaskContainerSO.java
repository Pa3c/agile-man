package pl.pa3c.agileman.api.taskcontainer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.BaseSO;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskContainerSO extends BaseSO<Long> {
	protected String title;
	private Long teamInProjectId;
	protected String type;
	protected Boolean closed;
	protected TaskContainerSO overcontainer;
	
}