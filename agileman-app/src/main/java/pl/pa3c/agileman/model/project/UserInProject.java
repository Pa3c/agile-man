package pl.pa3c.agileman.model.project;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.LongIdEntity;
import pl.pa3c.agileman.model.user.AppUser;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInProject extends LongIdEntity{
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="user_id")
	private AppUser user;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="team_in_project_id")
	private TeamInProject teamInProject;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_in_project_id")
	private List<RoleInProject> projectRoles;
}
