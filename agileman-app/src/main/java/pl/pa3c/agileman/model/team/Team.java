package pl.pa3c.agileman.model.team;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Team extends BaseLongEntity{
	private String description; 
	private String title;
}
