package pl.pa3c.agileman.model;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@MappedSuperclass
public abstract class BaseEntity<T> extends IdEntity<T>{

	@Version
	protected int version;
	@CreatedDate
	protected LocalDateTime creationDate;
	@LastModifiedDate
	protected LocalDateTime modificationDate;
	protected String createdBy;
	protected String modifiedBy;
}
