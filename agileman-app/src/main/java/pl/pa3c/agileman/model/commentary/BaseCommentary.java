package pl.pa3c.agileman.model.commentary;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import pl.pa3c.agileman.model.BaseEntity;

@MappedSuperclass
public abstract class BaseCommentary extends BaseEntity<Long>{
	
	protected String content;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	protected Scope scope;

}
