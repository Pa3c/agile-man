package pl.pa3c.agileman.api.taskcontainer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.IdSO;


@Data
@EqualsAndHashCode(callSuper = false)
public class StateSO extends IdSO<Long>{
	private String text;
	private Integer order;
}
