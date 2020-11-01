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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.auth.SignUpSO;
import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.api.team.TeamSO;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.api.user.UserTeamProjectSO;
import pl.pa3c.agileman.api.user.UserTeamSO;
import pl.pa3c.agileman.model.project.Project;
import pl.pa3c.agileman.model.project.RoleInProject;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.project.UserInProject;
import pl.pa3c.agileman.model.team.Team;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.model.user.UserRole;
import pl.pa3c.agileman.repository.RoleRepository;
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
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private SpringSecurityAuditorAware userAuditor;

	@Autowired
	public UserService(JpaRepository<AppUser, String> userRepository) {
		super(userRepository);
	}

	public UserSO register(SignUpSO signUpSO) {
		AppUser user = modelMapper.map(signUpSO, AppUser.class);
		user.setPassword(passwordEncoder.encode(signUpSO.getPassword()));
		user = commonRepository.save(user);
		UserRole role = new UserRole(user, roleRepository.getOne("COMMON"));
		role.setId(userRoleRepository.count() + 1);
		userRoleRepository.save(role);
		return modelMapper.map(commonRepository.getOne(user.getLogin()), UserSO.class);
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
			TeamSO teamSO = modelMapper.map(team, TeamSO.class);
			UserTeamProjectSO userTeamProjectSO = createProjectSO(project, x.getProjectRoles());
			UserTeamSO so = new UserTeamSO(teamSO, userTeamProjectSO);
			teamsOfUser.put(team.getId(), so);
		});

		return teamsOfUser.values();
	}

	private UserTeamProjectSO createProjectSO(Project project, Set<RoleInProject> roles) {
		if (project == null) {
			return null;
		}
		UserTeamProjectSO projectSO = new UserTeamProjectSO();
		projectSO.setId(project.getId());
		projectSO.setName(project.getTitle());
		projectSO.setRoles(roles.stream().map(x -> x.getRole().getId()).collect(Collectors.toSet()));
		return projectSO;
	}

	public List<ProjectSO> getProjectsOfUser(String login) {
		final List<UserInProject> userInProjects = getUserInProjects(login);
		return getTeamsInProject(userInProjects).map(x -> modelMapper.map(x.getProject(), ProjectSO.class))
				.collect(Collectors.toList());
	}

	public Stream<TeamInProject> getTeamsInProject(List<UserInProject> userInProjects) {
//		String loginInToken = userAuditor.getCurrentAuditor()
//				.orElseThrow(() -> new AgilemanException("Login in token not found"));
//		
//		if (!login.equals(loginInToken)) {
//			throw new AgilemanException(login + " is not equal to login in token: " + loginInToken);
//		}

		return userInProjects.stream().map(UserInProject::getTeamInProject);
	}

	public List<UserInProject> getUserInProjects(String login) {
		return userInProjectRepository.findByUserId(login);
	}
}
