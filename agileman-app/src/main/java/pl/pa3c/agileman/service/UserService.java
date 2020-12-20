package pl.pa3c.agileman.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.TitleNameSO;
import pl.pa3c.agileman.api.auth.SignUpSO;
import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;
import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.api.user.BaseUserSO;
import pl.pa3c.agileman.api.user.DetailedUserProjectSO;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.api.user.UserTeamProjectSO;
import pl.pa3c.agileman.api.user.UserTeamSO;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.model.base.LongIdEntity;
import pl.pa3c.agileman.model.project.Project;
import pl.pa3c.agileman.model.project.TeamProjectRole;
import pl.pa3c.agileman.model.project.ProjectType;
import pl.pa3c.agileman.model.project.RoleInProject;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.project.UserInProject;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.model.team.Team;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.model.user.UserRole;
import pl.pa3c.agileman.repository.ProjectRepository;
import pl.pa3c.agileman.repository.RoleInProjectRepository;
import pl.pa3c.agileman.repository.RoleRepository;
import pl.pa3c.agileman.repository.TaskContainerRepository;
import pl.pa3c.agileman.repository.TeamInProjectRepository;
import pl.pa3c.agileman.repository.UserInProjectRepository;
import pl.pa3c.agileman.repository.UserRoleRepository;
import pl.pa3c.agileman.repository.user.IBasicUserInfo;
import pl.pa3c.agileman.repository.user.UserRepository;
import pl.pa3c.agileman.security.UserCreds;

@Service
@Transactional
public class UserService extends CommonService<String, UserSO, AppUser> implements UserDetailsService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserInProjectRepository userInProjectRepository;

	@Autowired
	private RoleInProjectRepository roleInProjectRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private TeamInProjectRepository teamInProjectRepository;

	@Autowired
	private TaskContainerRepository taskContainerRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	public UserService(JpaRepository<AppUser, String> userRepository) {
		super(userRepository);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public UserSO register(SignUpSO signUpSO) {
		AppUser user = mapper.map(signUpSO, AppUser.class);
		user.setPassword(passwordEncoder.encode(signUpSO.getPassword()));
		user = repository.save(user);
		UserRole role = new UserRole(user, roleRepository.getOne("COMMON"));
		role.setId(userRoleRepository.count() + 1);
		userRoleRepository.save(role);
		return mapper.map(repository.getOne(user.getLogin()), UserSO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		return new UserCreds(findById(username), userRoleRepository.findByUserId(username));
	}

	public List<UserRole> findUserRoles(String username) {
		return userRoleRepository.findByUserId(username);
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<UserTeamSO> getTeamsOfUser(String login) {

		final Map<Long, UserTeamSO> teamsOfUser = new HashMap<>();
		getUserInProjects(login).forEach(x -> {

			final Project project = x.getTeamInProject().getProject();
			final Team team = x.getTeamInProject().getTeam();
			final String teamRole = roleInProjectRepository
					.findTeamRole(x.getId(), List.of(TeamProjectRole.TEAM_ADMIN, TeamProjectRole.TEAM_BASIC)).orElseGet(() -> {
						final RoleInProject rip = new RoleInProject();
						rip.setUserInProject(x);
						rip.setRole(TeamProjectRole.TEAM_BASIC);
						return rip;
					}).getRole().name();

			if (project.getId() == ProjectService.NO_PROJECT_ID && !teamsOfUser.containsKey(team.getId())) {
				teamsOfUser.put(team.getId(), userTeamSOWithoutProjects(team, teamRole));
				return;
			}

			if (teamsOfUser.containsKey(team.getId())) {
				teamsOfUser.get(team.getId()).getProjects().add(createProjectSO(project, userProjectRoles(x.getId())));
				return;
			}

			teamsOfUser.put(team.getId(), userTeamSO(team, project, x.getId(), teamRole));
		});

		return teamsOfUser.values();
	}

	public Set<ProjectSO> getProjectsOfUser(String login) {

		final List<UserInProject> userInProjects = getUserInProjects(login);
		final Set<ProjectSO> projectsWithUser = getTeamsInProject(userInProjects)
				.filter(x -> x.getProject().getId() > ProjectService.NO_PROJECT_ID)
				.map(x -> mapper.map(x.getProject(), ProjectSO.class)).collect(Collectors.toSet());

		final List<Project> projectCreatedByUser = projectRepository.findAllByCreatedBy(login);
		projectCreatedByUser.stream().forEach(x -> {
			projectsWithUser.add(mapper.map(x, ProjectSO.class));
		});

		return projectsWithUser;
	}

	public List<TitleNameSO<Long>> getProjectTeamsOfUser(String login, Long id) {
		return userInProjectRepository.findAllByUserIdAndTeamInProjectProjectId(login, id).stream()
				.map(x -> x.getTeamInProject().getTeam()).map(x -> new TitleNameSO<>(x.getId(), x.getTitle()))
				.collect(Collectors.toList());

	}

	public DetailedUserProjectSO getDetailedUserTeamProject(String login, Long projectId, Long teamId) {
		final TeamInProject teamInProject = findTeamInProject(projectId, teamId);
		final Long userInProjectId = findUserInProject(login, teamInProject.getId()).getId();
		final Set<String> roles = userProjectRoleNames(userInProjectId);
		final String projectType = teamInProject.getType().name();

		Stream<TaskContainer> streamForContainer = taskContainerRepository
				.findAllByTeamInProjectId(teamInProject.getId()).stream();

		if (!projectType.equals(ProjectType.XP.name())) {
			streamForContainer = removeOverContainers(streamForContainer);
		}

		final Set<TaskContainerSO> taskContainers = streamForContainer.map(x -> mapper.map(x, TaskContainerSO.class))
				.collect(Collectors.toSet());

		return createDetailedUserProject(teamInProject.getId(), teamInProject.getProject(), roles, projectType,
				taskContainers);
	}

	private TeamInProject findTeamInProject(Long projectId, Long teamId) {
		return teamInProjectRepository.findByProjectIdAndTeamId(projectId, teamId).orElseThrow(() -> {
			final String message = "project id " + projectId + " and team id " + teamId;
			throw new ResourceNotFoundException(new EmptyResultDataAccessException(1), message);
		});
	}

	private UserInProject findUserInProject(String login, Long id) {
		return userInProjectRepository.findByUserIdAndTeamInProjectId(login, id).orElseThrow(() -> {
			final String message = "login " + login + " and team in project id " + id;
			throw new ResourceNotFoundException(new EmptyResultDataAccessException(1), message);
		});

	}

	private DetailedUserProjectSO createDetailedUserProject(Long tipId, Project project, Set<String> roles,
			String projectType, Set<TaskContainerSO> taskContainers) {
		final DetailedUserProjectSO detailedUserProject = mapper.map(project, DetailedUserProjectSO.class);
		detailedUserProject.setTeamInProjectId(tipId);
		detailedUserProject.setRoles(roles);
		detailedUserProject.setType(projectType);
		detailedUserProject.setTaskContainers(taskContainers);
		return detailedUserProject;
	}

	private Stream<TaskContainer> removeOverContainers(Stream<TaskContainer> streamForContainer) {
		return streamForContainer.filter(x -> x.getOvercontainer() == null);
	}

	private UserTeamSO userTeamSO(final Team team, final Project project, final Long userInProjectId, String teamRole) {

		final UserTeamProjectSO userTeamProjectSO = createProjectSO(project,
				roleInProjectRepository.findAllByUserInProjectId(userInProjectId));

		return new UserTeamSO(mapper.map(team, TeamSO.class), teamRole, userTeamProjectSO);

	}

	private UserTeamSO userTeamSOWithoutProjects(final Team team, String teamRole) {
		return new UserTeamSO(mapper.map(team, TeamSO.class), teamRole, ProjectService.NO_PROJECTS);
	}

	private Collection<RoleInProject> userProjectRoles(Long userInProjectId) {
		return roleInProjectRepository.findAllByUserInProjectId(userInProjectId);
	}

	private Set<String> userProjectRoleNames(Long userInProjectId) {
		return userProjectRoles(userInProjectId).stream().map(x -> x.getRole().name()).collect(Collectors.toSet());
	}

	private Stream<TeamInProject> getTeamsInProject(List<UserInProject> userInProjects) {
		return userInProjects.stream().map(UserInProject::getTeamInProject);
	}

	private List<UserInProject> getUserInProjects(String login) {
		return userInProjectRepository.findByUserId(login);
	}

	private UserTeamProjectSO createProjectSO(Project project, Collection<RoleInProject> roles) {
		if (project == null) {
			return null;
		}
		final UserTeamProjectSO projectSO = new UserTeamProjectSO();
		projectSO.setId(project.getId());
		projectSO.setTitle(project.getTitle());
		projectSO.setRoles(roles.stream().map(x -> x.getRole().name()).collect(Collectors.toSet()));
		return projectSO;
	}

	public BaseUserSO getBasicInfo(String login) {
		final IBasicUserInfo userInfo = ((UserRepository) repository).getBasicInfo(login);
		return new BaseUserSO(userInfo.getId(), userInfo.getName(), userInfo.getSurname());
	}

	public List<BaseUserSO> getFilteredBasicInfo(String login) {
		final List<IBasicUserInfo> usersInfo = ((UserRepository) repository).getFilteredBasicInfo(login);
		return usersInfo.stream().map(x -> new BaseUserSO(x.getId(), x.getName(), x.getSurname()))
				.collect(Collectors.toList());
	}

}
