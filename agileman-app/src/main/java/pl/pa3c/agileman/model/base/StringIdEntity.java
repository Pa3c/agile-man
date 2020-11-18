package pl.pa3c.agileman.model.base;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class StringIdEntity implements IdEntity<String> {
	@Id
	protected String id;

}
