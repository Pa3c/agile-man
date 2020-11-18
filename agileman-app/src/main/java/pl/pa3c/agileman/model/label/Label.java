package pl.pa3c.agileman.model.label;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.StringIdEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Label extends StringIdEntity{
	private Type type;

}
