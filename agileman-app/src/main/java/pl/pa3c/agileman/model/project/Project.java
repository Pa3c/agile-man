package pl.pa3c.agileman.model.project;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Project extends BaseLongEntity{
	
	private String title;
	private String description;
}
