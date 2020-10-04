package pl.pa3c.agileman.model.label;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Label {
	private String name;
	private Type type;
}
