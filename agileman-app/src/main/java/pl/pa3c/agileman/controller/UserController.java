package pl.pa3c.agileman.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.TitleNameSO;
import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.api.user.DetailedUserProjectSO;
import pl.pa3c.agileman.api.user.UserSI;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.api.user.UserTeamSO;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.service.CommonService;
import pl.pa3c.agileman.service.UserService;

@RestController
@CrossOrigin
public class UserController extends CommonController<String, UserSO, AppUser> implements UserSI {

	@Autowired
	public UserController(CommonService<String, UserSO, AppUser> commonService) {
		super(commonService);
	}

	@Override
	public Collection<UserTeamSO> teamsOfUser(String login) {
		return ((UserService) commonService).getTeamsOfUser(login);
	}

	@Override
	public Set<ProjectSO> projectsOfUser(String login) {
		return ((UserService) commonService).getProjectsOfUser(login);
	}

	@Override
	public List<TitleNameSO<Long>> teamsOfUser(String login, Long id) {
		return ((UserService) commonService).getProjectTeamsOfUser(login, id);
	}

	@Override
	public DetailedUserProjectSO detailedUserTeamProject(String login, Long projectId, Long teamId) {
		return ((UserService) commonService).getDetailedUserTeamProject(login, projectId,teamId);
	}

}
