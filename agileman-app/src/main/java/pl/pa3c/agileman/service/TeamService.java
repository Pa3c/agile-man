package pl.pa3c.agileman.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.api.team.TeamWithUsersSO;
import pl.pa3c.agileman.api.user.RoleBaseUserSO;
import pl.pa3c.agileman.controller.exception.BadRequestException;
import pl.pa3c.agileman.controller.exception.UnconsistentDataException;
import pl.pa3c.agileman.model.project.ProjectType;
import pl.pa3c.agileman.model.project.RoleInProject;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.project.TeamProjectRole;
import pl.pa3c.agileman.model.project.UserInProject;
import pl.pa3c.agileman.model.team.Team;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.ProjectRepository;
import pl.pa3c.agileman.repository.RoleInProjectRepository;
import pl.pa3c.agileman.repository.TeamInProjectRepository;
import pl.pa3c.agileman.repository.UserInProjectRepository;
import pl.pa3c.agileman.repository.user.UserRepository;

@Service
public class TeamService extends CommonService<Long, TeamSO, Team> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInProjectRepository userInProjectRepository;

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
//
//	@Transactional
//	public TeamWithUsersSO addUserToTeam(Long id, String login) {
//		final Team team = repository.getOne(id);
//		final AppUser user = userRepository.getOne(login);
//		final Set<TeamInProject> teamInProjects = userInProjectRepository.findDistinctByTeamInProjectTeamId(id).stream()
//				.map(UserInProject::getTeamInProject).collect(Collectors.toSet());
//
//		final String basic = "BASIC";
//		final String teamLead = "TEAM_LEAD";
//
//		final RoleInProject roleInProject = new RoleInProject();
//		if (team.getCreatedBy().equals(login)) {
//			roleInProject.setRole(projectRoleRepository.getOne(teamLead));
//		} else {
//			roleInProject.setRole(projectRoleRepository.getOne(basic));
//		}
//
//		if (teamInProjects.size() == 0) {
//
//		}
//		teamInProjects.forEach(x -> {
//			final UserInProject uip = new UserInProject();
//			uip.setUser(user);
//			uip.setTeamInProject(x);
//			final UserInProject savedUiP = userInProjectRepository.save(uip);
//			roleInProject.setUserInProject(savedUiP);
//			roleInProjectRepository.save(roleInProject);
//		});
//
//		final TeamWithUsersSO teamWithUsersSO = mapper.map(team, TeamWithUsersSO.class);
//		teamWithUsersSO.setUsers(
//				usersFromProjects(id).stream().map(x -> mapper.map(x, UserSO.class)).collect(Collectors.toSet()));
//
//		return teamWithUsersSO;
//	}

//	private Set<AppUser> usersFromProjects(long teamId) {
//		return userInProjectRepository.findDistinctByTeamInProjectTeamId(teamId).stream().map(UserInProject::getUser)
//				.collect(Collectors.toSet());
//	}

	@Transactional
	public TeamWithUsersSO createTeamWithUsers(TeamWithUsersSO teamWithUsersSO) {
		TeamSO createdTeam = super.create(teamWithUsersSO);

		final TeamInProject teamInProject = new TeamInProject();
		teamInProject.setProject(projectRepository.getOne(ProjectService.NO_PROJECT_ID));
		teamInProject.setTeam(repository.getOne(createdTeam.getId()));
		teamInProject.setType(ProjectType.BASIC_TEAM);

		final TeamInProject savedTiP = teamInProjectRepository.save(teamInProject);

		teamWithUsersSO.getUsers().forEach(x -> {
			final UserInProject userInProject = new UserInProject();
			userInProject.setTeamInProject(savedTiP);
			userInProject.setUser(userRepository.getOne(x.getId()));
			userInProjectRepository.save(userInProject);

			final TeamProjectRole teamRole = TeamProjectRole.valueOf(x.getRole());
			if (!teamRole.name().startsWith(RoleInProject.TEAM_PREIX)) {
				throw new BadRequestException(
						"When creating a team it can only have users with team related roles ! Was: " + x.getRole());
			}

			final RoleInProject roleInProject = new RoleInProject();
			roleInProject.setUserInProject(userInProject);
			roleInProject.setRole(teamRole);
			roleInProjectRepository.save(roleInProject);
		});

		return teamWithUsersSO;
	}

	public TeamWithUsersSO getTeamWithUsers(Long id) {
		final Team team = super.findById(id);
		final TeamInProject tip = teamInProjectRepository.findByProjectIdAndTeamId(ProjectService.NO_PROJECT_ID, id)
				.orElseThrow(() -> new UnconsistentDataException(
						"There is a team with id: " + id + " without basic project."));

		final List<UserInProject> uips = userInProjectRepository.findAllByTeamInProjectId(tip.getId());
		final TeamWithUsersSO teamWithUsersSO = mapper.map(team, TeamWithUsersSO.class);
		teamWithUsersSO.setUsers(new ArrayList<>());

		uips.forEach(x -> {
			final String role = roleInProjectRepository
					.findTeamRole(x.getId(), List.of(TeamProjectRole.TEAM_ADMIN, TeamProjectRole.TEAM_BASIC))
					.orElseGet(() -> {
						final RoleInProject rip = new RoleInProject();
						rip.setUserInProject(x);
						rip.setRole(TeamProjectRole.TEAM_BASIC);
						return rip;
					}).getRole().name();

			final RoleBaseUserSO roleBaseUserSO = mapper.map(x.getUser(), RoleBaseUserSO.class);
			roleBaseUserSO.setRole(role);
			teamWithUsersSO.getUsers().add(roleBaseUserSO);
		});

		return teamWithUsersSO;
	}

	@Transactional
	public RoleBaseUserSO addUserToTeam(Long id, RoleBaseUserSO roleBaseUserSO) {
		final Optional<Boolean> consistenceErrorFlag = Optional.of(Boolean.FALSE);
		final AppUser user = userRepository.getOne(roleBaseUserSO.getId());
		final List<TeamInProject> tips = teamInProjectRepository.findAllByTeamId(id);

		tips.forEach(x -> {
			if (x.getProject().getId() > ProjectService.NO_PROJECT_ID) {
				createRoleInProject(createUserInProject(x, user), TeamProjectRole.COMMON);
				return;
			}
			createRoleInProject(createUserInProject(x, user), TeamProjectRole.TEAM_BASIC);
			consistenceErrorFlag.ifPresent(flag -> flag = Boolean.TRUE);
		});

		if (!consistenceErrorFlag.get().booleanValue()) {
			return roleBaseUserSO;
		}

		throw new UnconsistentDataException("There is a team with id: " + id + " without basic project.");
	}

	@Transactional
	public void deleteUserFromTeam(Long id, String login) {
		final List<TeamInProject> tips = teamInProjectRepository.findAllByTeamId(id);
		final List<UserInProject> uips = userInProjectRepository.findAllByUserIdAndTeamInProjectIn(login, tips);
		uips.forEach(x -> roleInProjectRepository.deleteByUserInProjectId(x.getId()));
		userInProjectRepository.deleteAll(uips);
	}

	private void createRoleInProject(UserInProject userInProject, TeamProjectRole role) {
		final RoleInProject rip = new RoleInProject();
		rip.setUserInProject(userInProject);
		rip.setRole(role);
		roleInProjectRepository.save(rip);
	}

	private UserInProject createUserInProject(TeamInProject x, AppUser user) {
		final UserInProject uip = new UserInProject();
		uip.setTeamInProject(x);
		uip.setUser(user);
		return userInProjectRepository.save(uip);
	}

}
