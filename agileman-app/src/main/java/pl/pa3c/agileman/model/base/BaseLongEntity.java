package pl.pa3c.agileman.model.base;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseLongEntity extends LongIdEntity{

	@Version
	protected int version;
	@CreatedDate
	@Column( nullable = false, updatable = false)
	protected LocalDateTime creationDate;
	@LastModifiedDate
	protected LocalDateTime modificationDate;
	@CreatedDate
	protected String createdBy;
	@LastModifiedBy
	protected String modifiedBy;
}
