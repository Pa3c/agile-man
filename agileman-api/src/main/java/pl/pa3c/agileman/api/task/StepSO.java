package pl.pa3c.agileman.api.task;

import lombok.Data;
import pl.pa3c.agileman.api.BaseSO;

@Data
public class StepSO extends BaseSO<Long>{
	private String description;
	private Long taskId;
	private boolean done;
	private StepSO overstep;
	private Integer order;
}
