package pl.pa3c.agileman.model.label;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.IdEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Label extends IdEntity<String>{
	private Type type;
	
	public String getName() {
		return id;
	}
	
	public void setName(String name) {
		this.id = name;
	}
}
