package pl.pa3c.agileman.api.taskcontainer;

import java.time.LocalDateTime;

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
	
	//THIS IS FOR XP PURPOSES
	private Boolean abandoned;

	protected TaskContainerSO overcontainer;
	
	//THIS IS ONLY FOR SCRUM PURPOSES
	private LocalDateTime openDate;
	private LocalDateTime closeDate;
	
}