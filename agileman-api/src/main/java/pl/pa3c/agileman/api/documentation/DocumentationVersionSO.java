package pl.pa3c.agileman.api.documentation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.BaseSO;

@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentationVersionSO extends BaseSO<Long>{
	private String content;
	private Long resourceId;
}
