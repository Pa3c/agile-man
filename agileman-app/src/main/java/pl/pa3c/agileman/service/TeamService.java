package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.model.team.Team;

@Service
public class TeamService extends CommonService<Long, TeamSO, Team>{


	@Autowired
	public TeamService(JpaRepository<Team, Long> teamRepository) {
		super(teamRepository);
	}

}
