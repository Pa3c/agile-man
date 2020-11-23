package pl.pa3c.agileman.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.api.user.UserTeamProjectSO;
import pl.pa3c.agileman.model.project.Project;
import pl.pa3c.agileman.model.project.ProjectRole;
import pl.pa3c.agileman.model.project.ProjectType;
import pl.pa3c.agileman.model.project.RoleInProject;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.project.UserInProject;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.ProjectRoleRepository;
import pl.pa3c.agileman.repository.RoleInProjectRepository;
import pl.pa3c.agileman.repository.TeamInProjectRepository;
import pl.pa3c.agileman.repository.TeamRepository;
import pl.pa3c.agileman.repository.UserInProjectRepository;

@Service
public class ProjectService extends CommonService<Long, ProjectSO, Project> {
	public static final long NO_PROJECT_ID = -1;
	public static final UserTeamProjectSO[] NO_PROJECTS = {};

	@Autowired
	private UserInProjectRepository userInProjectRepository;

	@Autowired
	private TeamInProjectRepository teamInProjectRepository;

	@Autowired
	private ProjectRoleRepository projectRoleRepository;

	@Autowired
	private RoleInProjectRepository roleInProjectRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	public ProjectService(JpaRepository<Project, Long> projectRepository) {
		super(projectRepository);
	}

	@Transactional
	public void addTeamToProject(Long projectId, Long teamId, String type) {

		final Project project = commonRepository.getOne(projectId);
		final TeamInProject teamInProject = new TeamInProject();
		teamInProject.setProject(project);
		teamInProject.setTeam(teamRepository.getOne(teamId));
		teamInProject.setType(ProjectType.valueOf(type));

		final TeamInProject savedTiP = teamInProjectRepository.save(teamInProject);
		
		final Set<AppUser> usersFromTeam = userInProjectRepository
				.findDistinctByTeamInProjectTeamId(teamId).stream().map(UserInProject::getUser).collect(Collectors.toSet());
		
		final String basic = "BASIC";
		final String admin = "ADMIN";
		final RoleInProject roleInProject = new RoleInProject();
		final ProjectRole adminRole = projectRoleRepository.getOne(admin);
		final ProjectRole basicRole = projectRoleRepository.getOne(basic);

		usersFromTeam.forEach(x -> {
			final UserInProject uip = new UserInProject();
			uip.setUser(x);
			uip.setTeamInProject(savedTiP);
			final UserInProject savedUiP = userInProjectRepository.save(uip);
			
			if (x.getLogin().equals(project.getCreatedBy())) {
				roleInProject.setRole(adminRole);
			} else {
				roleInProject.setRole(basicRole);
			}
			roleInProject.setUserInProject(savedUiP);
			roleInProjectRepository.save(roleInProject);
		});
	}

}
