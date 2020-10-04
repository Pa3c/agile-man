package pl.pa3c.agileman.model.team;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.IdEntity;
import pl.pa3c.agileman.model.user.User;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamInProject extends IdEntity<Long>{
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="project_id")
	private User project;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="team_id")
	private Team team;
}
