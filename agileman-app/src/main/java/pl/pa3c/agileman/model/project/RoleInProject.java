package pl.pa3c.agileman.model.project;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.model.base.LongIdEntity;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleInProject extends LongIdEntity {
	public static final String TEAM_PREIX = "TEAM_";
	@NotNull
	@Enumerated(EnumType.STRING)
	private TeamProjectRole role;
	@ManyToOne
	@JoinColumn(name = "user_in_project_id")
	private UserInProject userInProject;
}
