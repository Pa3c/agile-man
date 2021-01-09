package pl.pa3c.agileman.api.commentary;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DocIdCommentary extends CommentarySO {
	public DocIdCommentary(CommentarySO so) {
		this.content = so.content;
		this.login = so.login;
		this.resourceId = so.resourceId;
		this.isPublic = so.isPublic;
		this.id = so.getId();
		this.createdBy = so.getCreatedBy();
		this.version = so.getVersion();
		this.modificationDate = so.getModificationDate();
		this.modifiedBy = so.getModifiedBy();
		this.creationDate = so.getCreationDate();
	}
}
