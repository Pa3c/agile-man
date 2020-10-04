package pl.pa3c.agileman.model.project;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.BaseEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Project extends BaseEntity<Long>{
	
	private String title;
	private String description;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;
	
}
