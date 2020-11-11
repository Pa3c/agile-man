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
import pl.pa3c.agileman.api.user.DetailedUserProjectSO;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.api.user.UserTeamProjectSO;
import pl.pa3c.agileman.api.user.UserTeamSO;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.model.project.Project;
import pl.pa3c.agileman.model.project.ProjectType;
import pl.pa3c.agileman.model.project.RoleInProject;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.project.UserInProject;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.model.team.Team;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.model.user.UserRole;
import pl.pa3c.agileman.repository.RoleRepository;
import pl.pa3c.agileman.repository.TeamInProjectRepository;
import pl.pa3c.agileman.repository.UserInProjectRepository;
import pl.pa3c.agileman.repository.UserRoleRepository;
import pl.pa3c.agileman.security.SpringSecurityAuditorAware;
import pl.pa3c.agileman.security.UserCreds;

@Service
@Transactional
public class UserService extends CommonService<String, UserSO, AppUser> implements UserDetailsService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserInProjectRepository userInProjectRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private TeamInProjectRepository teamInProjectRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SpringSecurityAuditorAware userAuditor;

	@Autowired
	public UserService(JpaRepository<AppUser, String> userRepository) {
		super(userRepository);
	}

	public UserSO register(SignUpSO signUpSO) {
		AppUser user = mapper.map(signUpSO, AppUser.class);
		user.setPassword(passwordEncoder.encode(signUpSO.getPassword()));
		user = commonRepository.save(user);
		UserRole role = new UserRole(user, roleRepository.getOne("COMMON"));
		role.setId(userRoleRepository.count() + 1);
		userRoleRepository.save(role);
		return mapper.map(commonRepository.getOne(user.getLogin()), UserSO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		return new UserCreds(findById(username), userRoleRepository.findByUserId(username));
	}

	public List<UserRole> findUserRoles(String username) {
		return userRoleRepository.findByUserId(username);
	}

	public Collection<UserTeamSO> getTeamsOfUser(String login) {
		final List<UserInProject> userInProjects = getUserInProjects(login);

		final Map<Long, UserTeamSO> teamsOfUser = new HashMap<>();
		userInProjects.forEach(x -> {

			final Project project = x.getTeamInProject().getProject();
			final Team team = x.getTeamInProject().getTeam();
			if (teamsOfUser.containsKey(team.getId())) {
				teamsOfUser.get(team.getId()).getProjects().add(createProjectSO(project, x.getProjectRoles()));
				return;
			}
			TeamSO teamSO = mapper.map(team, TeamSO.class);
			UserTeamProjectSO userTeamProjectSO = createProjectSO(project, x.getProjectRoles());
			teamsOfUser.put(team.getId(), new UserTeamSO(teamSO, userTeamProjectSO));
		});

		return teamsOfUser.values();
	}

	public Set<ProjectSO> getProjectsOfUser(String login) {
		final List<UserInProject> userInProjects = getUserInProjects(login);
		return getTeamsInProject(userInProjects).map(x -> mapper.map(x.getProject(), ProjectSO.class))
				.collect(Collectors.toSet());
	}

	private Stream<TeamInProject> getTeamsInProject(List<UserInProject> userInProjects) {
		return userInProjects.stream().map(UserInProject::getTeamInProject);
	}

	private List<UserInProject> getUserInProjects(String login) {
		return userInProjectRepository.findByUserId(login);
	}

	public List<TitleNameSO<Long>> getProjectTeamsOfUser(String login, Long id) {
		final Set<UserInProject> userInProjects = userInProjectRepository
				.findAllByUserIdAndTeamInProjectProjectId(login, id);

		List<TitleNameSO<Long>> xx = userInProjects.stream().map(x -> {
			Team t = x.getTeamInProject().getTeam();
			TitleNameSO<Long> titleName = new TitleNameSO<>();
			titleName.setId(id);
			titleName.setTitle(t.getTitle());
			return titleName;
		}).collect(Collectors.toList());

		return xx;
	}

	public DetailedUserProjectSO getProjectTeamOfUser(String login, Long projectId, Long teamId) {
		final TeamInProject teamInProject = teamInProjectRepository.findByProjectIdAndTeamId(projectId, teamId)
				.orElseThrow(() -> {
					final short EXPECTED_SIZE = 1;
					final String ERROR_MESSAGE = "project id " + projectId + " and team id " + teamId;
					throw new ResourceNotFoundException(new EmptyResultDataAccessException(EXPECTED_SIZE),
							ERROR_MESSAGE);
				});

		final UserInProject userInProject = userInProjectRepository
				.findByUserIdAndTeamInProjectId(login, teamInProject.getId()).orElseThrow(() -> {
					final short EXPECTED_SIZE = 1;
					final String ERROR_MESSAGE = "login " + login + " and team in project id " + teamInProject.getId();
					throw new ResourceNotFoundException(new EmptyResultDataAccessException(EXPECTED_SIZE),
							ERROR_MESSAGE);
				});

		final Set<String> roles = userInProject.getProjectRoles().stream().map(x -> x.getRole().getId())
				.collect(Collectors.toSet());
		final String projectType = teamInProject.getType().name();

		Stream<TaskContainer> streamForContainer = teamInProject.getTaskContainers().stream();
		if (!projectType.equals(ProjectType.XP.name())) {
			streamForContainer = streamForContainer.filter(x -> x.getOvercontainer() == null);
		}

		final Set<TaskContainerSO> taskContainers = streamForContainer.map(x -> mapper.map(x, TaskContainerSO.class))
				.collect(Collectors.toSet());

		final DetailedUserProjectSO detailedUserProject = mapper.map(teamInProject.getProject(),
				DetailedUserProjectSO.class);
		detailedUserProject.setRoles(roles);
		detailedUserProject.setType(projectType);
		detailedUserProject.setTaskContainers(taskContainers);

		return detailedUserProject;
	}

	private UserTeamProjectSO createProjectSO(Project project, Collection<RoleInProject> roles) {
		if (project == null) {
			return null;
		}
		final UserTeamProjectSO projectSO = new UserTeamProjectSO();
		projectSO.setId(project.getId());
		projectSO.setTitle(project.getTitle());
		projectSO.setRoles(roles.stream().map(x -> x.getRole().getId()).collect(Collectors.toSet()));
		return projectSO;
	}

}
