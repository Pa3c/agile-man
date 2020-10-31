package pl.pa3c.agileman.service;

import java.util.List;
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
import pl.pa3c.agileman.controller.exception.AgilemanException;
import pl.pa3c.agileman.model.team.TeamInProject;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.model.user.UserInProject;
import pl.pa3c.agileman.model.user.UserRole;
import pl.pa3c.agileman.repository.RoleRepository;
import pl.pa3c.agileman.repository.UserInProjectRepository;
import pl.pa3c.agileman.repository.UserRoleRepository;
import pl.pa3c.agileman.security.UserCreds;
import pl.pa3c.agileman.security.SpringSecurityAuditorAware;

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

	public List<TeamSO> getTeamsOfUser(String login) {

		return getTeamsInProject(login).map(x -> modelMapper.map(x.getTeam(), TeamSO.class))
				.collect(Collectors.toList());
	}

	public List<ProjectSO> getProjectsOfUser(String login) {
		return getTeamsInProject(login).map(x -> modelMapper.map(x.getProject(), ProjectSO.class))
				.collect(Collectors.toList());
	}

	public Stream<TeamInProject> getTeamsInProject(String login) {
//		String loginInToken = userAuditor.getCurrentAuditor()
//				.orElseThrow(() -> new AgilemanException("Login in token not found"));
//		
//		if (!login.equals(loginInToken)) {
//			throw new AgilemanException(login + " is not equal to login in token: " + loginInToken);
//		}

		return userInProjectRepository.findAllByUserId(login).stream().map(UserInProject::getTeamInProject);
	}
}
