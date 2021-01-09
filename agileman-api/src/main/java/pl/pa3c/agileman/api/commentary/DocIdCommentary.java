package pl.pa3c.agileman.api.commentary;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DocIdCommentary extends CommentarySO {
	private Long resourceId;
}
