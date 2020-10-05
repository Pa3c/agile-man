package pl.pa3c.agileman.model.team;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.IdEntity;
import pl.pa3c.agileman.model.user.AppUser;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamInProject extends IdEntity<Long>{
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="project_id")
	private AppUser project;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="team_id")
	private Team team;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;
	
}
