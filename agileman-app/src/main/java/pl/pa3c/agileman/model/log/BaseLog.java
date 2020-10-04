package pl.pa3c.agileman.model.log;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import pl.pa3c.agileman.model.IdEntity;

@MappedSuperclass
public abstract class BaseLog extends IdEntity<Long>{
	protected LocalDateTime date;
	protected String content;
}
