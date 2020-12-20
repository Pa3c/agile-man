package pl.pa3c.agileman.api.user;


import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.team.TeamSO;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserTeamSO extends TeamSO {
	public UserTeamSO(TeamSO teamSO,String teamRole, UserTeamProjectSO... projects) {
		super(teamSO);
		this.projects = new HashSet<>();
		this.teamRole = teamRole;
		for(UserTeamProjectSO project: projects) {
			this.projects.add(project);
		}
	}

	private Set<UserTeamProjectSO> projects = new HashSet<>();
	private String teamRole;
}
