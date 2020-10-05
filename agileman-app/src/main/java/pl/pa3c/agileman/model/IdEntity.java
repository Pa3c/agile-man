package pl.pa3c.agileman.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;


@MappedSuperclass
@Data
public abstract class IdEntity<T> {
	
	@Id
	protected T id;
}
