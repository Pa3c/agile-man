package pl.pa3c.agileman.model.log;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import pl.pa3c.agileman.model.base.LongIdEntity;

@MappedSuperclass
public abstract class BaseLog extends LongIdEntity{
	protected LocalDateTime date;
	protected String content;
}
