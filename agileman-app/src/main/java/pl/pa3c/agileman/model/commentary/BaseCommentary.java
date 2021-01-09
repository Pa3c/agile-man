package pl.pa3c.agileman.model.commentary;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;

@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
@Data
public abstract class BaseCommentary extends BaseLongEntity{
	
	protected String content;
	protected String login;
	protected Boolean isPublic;
	

}
