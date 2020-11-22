package pl.pa3c.agileman.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.api.team.TeamWithUsersSO;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.model.project.ProjectType;
import pl.pa3c.agileman.model.project.RoleInProject;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.project.UserInProject;
import pl.pa3c.agileman.model.team.Team;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.ProjectRepository;
import pl.pa3c.agileman.repository.ProjectRoleRepository;
import pl.pa3c.agileman.repository.RoleInProjectRepository;
import pl.pa3c.agileman.repository.TeamInProjectRepository;
import pl.pa3c.agileman.repository.UserInProjectRepository;
import pl.pa3c.agileman.repository.UserRepository;

@Service
public class TeamService extends CommonService<Long, TeamSO, Team> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInProjectRepository userInProjectRepository;

	@Autowired
	private ProjectRoleRepository projectRoleRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private RoleInProjectRepository roleInProjectRepository;

	@Autowired
	private TeamInProjectRepository teamInProjectRepository;

	@Autowired
	public TeamService(JpaRepository<Team, Long> teamRepository) {
		super(teamRepository);
	}

	@Transactional
	public TeamWithUsersSO addUserToTeam(Long id, String login) {
		final Team team = commonRepository.getOne(id);
		final AppUser user = userRepository.getOne(login);
		final Set<TeamInProject> teamInProjects = userInProjectRepository.findDistinctByTeamInProjectTeamId(id).stream()
				.map(UserInProject::getTeamInProject).collect(Collectors.toSet());

		final String basic = "BASIC";
		final String teamLead = "TEAM_LEAD";

		final RoleInProject roleInProject = new RoleInProject();
		if (team.getCreatedBy().equals(login)) {
			roleInProject.setRole(projectRoleRepository.getOne(teamLead));
		} else {
			roleInProject.setRole(projectRoleRepository.getOne(basic));
		}

		if (teamInProjects.size() == 0) {

		}
		teamInProjects.forEach(x -> {
			final UserInProject uip = new UserInProject();
			uip.setUser(user);
			uip.setTeamInProject(x);
			final UserInProject savedUiP = userInProjectRepository.save(uip);
			roleInProject.setUserInProject(savedUiP);
			roleInProjectRepository.save(roleInProject);
		});

		final TeamWithUsersSO teamWithUsersSO = mapper.map(team, TeamWithUsersSO.class);
		teamWithUsersSO.setUsers(
				usersFromProjects(id).stream().map(x -> mapper.map(x, UserSO.class)).collect(Collectors.toSet()));

		return teamWithUsersSO;
	}

	private Set<AppUser> usersFromProjects(long teamId) {
		return userInProjectRepository.findDistinctByTeamInProjectTeamId(teamId).stream().map(UserInProject::getUser)
				.collect(Collectors.toSet());
	}

	@Transactional
	public TeamWithUsersSO createTeamWithUsers(TeamWithUsersSO teamWithUsersSO) {
		TeamSO createdTeam = super.create(teamWithUsersSO);

		final TeamInProject teamInProject = new TeamInProject();
		teamInProject.setProject(projectRepository.getOne(ProjectService.NO_PROJECT_ID));
		teamInProject.setTeam(commonRepository.getOne(createdTeam.getId()));
		teamInProject.setType(ProjectType.BASIC_TEAM);
		final TeamInProject savedTiP = teamInProjectRepository.save(teamInProject);

		teamWithUsersSO.getUsers().forEach(x -> {
			UserInProject userInProject = new UserInProject();
			userInProject.setTeamInProject(savedTiP);
			userInProject.setUser(userRepository.getOne(x.getId()));
			userInProjectRepository.save(userInProject);
		});

		return teamWithUsersSO;
	}

}
