package pl.pa3c.agileman.api.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.TitleNameSO;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class BaseProjectTeamSO extends TitleNameSO<Long>{
	private String type;
}
