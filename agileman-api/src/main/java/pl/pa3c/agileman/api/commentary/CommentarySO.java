package pl.pa3c.agileman.api.commentary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.BaseSO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentarySO extends BaseSO<Long>{
	protected String content;
	protected String scope;
	protected String login;
	protected Long resourceId;
}
