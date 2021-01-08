package pl.pa3c.agileman.model.commentary;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;

@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public abstract class BaseCommentary extends BaseLongEntity{
	
	protected String content;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	protected Scope scope;

}
