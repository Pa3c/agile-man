package pl.pa3c.agileman.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.TitleNameSO;
import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.api.team.TeamWithUsersSO;
import pl.pa3c.agileman.api.user.RoleBaseUserSO;
import pl.pa3c.agileman.controller.exception.BadRequestException;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
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
import pl.pa3c.agileman.repository.team.TeamRepository;
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
			if (!teamRole.name().startsWith(RoleInProject.TEAM_PREFIX)) {
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
	public RoleBaseUserSO updateUserRole(Long id, RoleBaseUserSO roleBaseUserSO) {
		final TeamInProject tip = teamInProjectRepository.findByProjectIdAndTeamId(ProjectService.NO_PROJECT_ID, id)
				.orElseThrow(() -> new UnconsistentDataException(
						"There is a team with id: " + id + " without basic project."));
		UserInProject uip = userInProjectRepository.findByUserIdAndTeamInProjectId(roleBaseUserSO.getId(), tip.getId())
				.orElseThrow(() -> new UnconsistentDataException(
						"There is no team with id: " + id + " and user " + roleBaseUserSO.getId()));

		roleInProjectRepository
				.findTeamRole(uip.getId(), List.of(TeamProjectRole.TEAM_ADMIN, TeamProjectRole.TEAM_BASIC))
				.ifPresentOrElse(x -> x.setRole(TeamProjectRole.valueOf(roleBaseUserSO.getRole().toUpperCase())),
						() -> {
							throw new ResourceNotFoundException("There is no user "+roleBaseUserSO.getId()+" in a team with roles BASIC or ADMIN");
						});

		final RoleBaseUserSO returnedRoleBaseUserSO = mapper.map(uip.getUser(), RoleBaseUserSO.class);
		returnedRoleBaseUserSO.setRole(roleBaseUserSO.getRole().toUpperCase());

		return returnedRoleBaseUserSO;
	}

	@Transactional
	public void deleteUserFromTeam(Long id, String login) {
		final List<TeamInProject> tips = teamInProjectRepository.findAllByTeamId(id);
		final List<UserInProject> uips = userInProjectRepository.findAllByUserIdAndTeamInProjectIn(login, tips);
		userInProjectRepository.deleteAll(uips);
	}

	public List<TitleNameSO<Long>> getFilteredBasicTeam(String value) {
		Long teamId = -1l;
		if (value.matches("\\d+")) {
			teamId = Long.parseLong(value);
		}

		return ((TeamRepository) repository).getFilteredBasicTeam(teamId, value).stream()
				.map(x -> new TitleNameSO<Long>(x.getId(), x.getTitle())).collect(Collectors.toList());
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
