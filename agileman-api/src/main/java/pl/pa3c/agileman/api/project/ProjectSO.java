package pl.pa3c.agileman.api.project;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.BaseSO;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProjectSO extends BaseSO<Long>{
	protected String title;
	protected String description;
}
