package pl.pa3c.agileman.api.team;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.BaseSO;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeamSO extends BaseSO<Long>{
	private String description; 
	private String title;
}
