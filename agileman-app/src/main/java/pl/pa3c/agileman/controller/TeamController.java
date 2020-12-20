package pl.pa3c.agileman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.TitleNameSO;
import pl.pa3c.agileman.api.team.TeamSI;
import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.api.team.TeamWithUsersSO;
import pl.pa3c.agileman.api.user.RoleBaseUserSO;
import pl.pa3c.agileman.model.team.Team;
import pl.pa3c.agileman.service.CommonService;
import pl.pa3c.agileman.service.TeamService;

@RestController
@CrossOrigin
public class TeamController extends CommonController<Long, TeamSO, Team> implements TeamSI{

	@Autowired
	public TeamController(CommonService<Long, TeamSO, Team> commonService) {
		super(commonService);
	}

	@Override
	public TeamWithUsersSO createTeamWithUsers(TeamWithUsersSO teamWithUsersSO) {
		
		return ((TeamService)commonService).createTeamWithUsers(teamWithUsersSO);
	}

	@Override
	public TeamWithUsersSO getTeamWithUsers(Long id) {
		return ((TeamService)commonService).getTeamWithUsers(id);
	}

	@Override
	public RoleBaseUserSO addUserToTeam(Long id, RoleBaseUserSO roleBaseUserSO) {
		return ((TeamService)commonService).addUserToTeam(id,roleBaseUserSO);
	}

	@Override
	public void deleteUserFromTeam(Long id, String login) {
		((TeamService)commonService).deleteUserFromTeam(id,login);
	}

	@Override
	public List<TitleNameSO<Long>> getFilteredBasicTeam(String value) {
		
		return ((TeamService) commonService).getFilteredBasicTeam(value);
	}
	
}

