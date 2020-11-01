package pl.pa3c.agileman.api.team;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.BaseSO;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TeamSO extends BaseSO<Long> {
	public TeamSO(TeamSO teamSO) {
		super(teamSO);
		this.title = teamSO.title;
		this.description = teamSO.description;
	}
	private String title;
	private String description;
}
