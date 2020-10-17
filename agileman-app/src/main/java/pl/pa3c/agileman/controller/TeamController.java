package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.team.TeamSI;
import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.model.team.Team;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class TeamController extends CommonController<Long, TeamSO, Team> implements TeamSI{

	@Autowired
	public TeamController(CommonService<Long, TeamSO, Team> commonService) {
		super(commonService);
	}

}