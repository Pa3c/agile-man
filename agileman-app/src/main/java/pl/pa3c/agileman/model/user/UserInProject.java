package pl.pa3c.agileman.model.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.IdEntity;
import pl.pa3c.agileman.model.team.TeamInProject;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInProject extends IdEntity<Long>{

	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="user_id")
	private AppUser user;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="team_in_project_id")
	private TeamInProject teamInProject;
}
