package pl.pa3c.agileman.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.IdEntity;
import pl.pa3c.agileman.model.base.StringIdEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends StringIdEntity{
	
}
