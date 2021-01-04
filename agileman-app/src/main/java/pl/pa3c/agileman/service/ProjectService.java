package pl.pa3c.agileman.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.label.LabelSO;
import pl.pa3c.agileman.api.project.BaseProjectTeamSO;
import pl.pa3c.agileman.api.project.ProjectLabelSO;
import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.api.project.ProjectUserRolesInfoSO;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;
import pl.pa3c.agileman.api.user.MultiRoleBaseUserSO;
import pl.pa3c.agileman.api.user.UserTeamProjectSO;
import pl.pa3c.agileman.controller.exception.BadRequestException;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.model.label.ProjectLabel;
import pl.pa3c.agileman.model.project.Project;
import pl.pa3c.agileman.model.project.ProjectType;
import pl.pa3c.agileman.model.project.RoleInProject;
import pl.pa3c.agileman.model.project.RoleUtil;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.project.TeamProjectRole;
import pl.pa3c.agileman.model.project.UserInProject;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.model.taskcontainer.Type;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.ProjectLabelRepository;
import pl.pa3c.agileman.repository.RoleInProjectRepository;
import pl.pa3c.agileman.repository.TaskContainerRepository;
import pl.pa3c.agileman.repository.TeamInProjectRepository;
import pl.pa3c.agileman.repository.UserInProjectRepository;
import pl.pa3c.agileman.repository.team.TeamRepository;

@Service
public class ProjectService extends CommonService<Long, ProjectSO, Project> {
	public static final long NO_PROJECT_ID = -1;
	public static final UserTeamProjectSO[] NO_PROJECTS = {};

	@Autowired
	private UserInProjectRepository userInProjectRepository;

	@Autowired
	private TeamInProjectRepository teamInProjectRepository;

	@Autowired
	private RoleInProjectRepository roleInProjectRepository;

	@Autowired
	private TaskContainerRepository taskContainerRepository;

	@Autowired
	private ProjectLabelRepository projectLabelRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	public ProjectService(JpaRepository<Project, Long> projectRepository) {
		super(projectRepository);
	}

	@Transactional
	public void addLabels(Long projectId, List<LabelSO> labels) {
		final Project project = repository.getOne(projectId);
		labels.forEach(x -> projectLabelRepository
				.save(new ProjectLabel(x.getId(), pl.pa3c.agileman.model.label.Type.valueOf(x.getType()), project)));
	}

	@Transactional
	public void addTeam(Long projectId, Long teamId, String type) {

		final Project project = repository.getOne(projectId);
		final TeamInProject teamInProject = new TeamInProject();
		teamInProject.setProject(project);
		teamInProject.setTeam(teamRepository.getOne(teamId));
		teamInProject.setType(ProjectType.valueOf(type));

		final TeamInProject savedTiP = teamInProjectRepository.save(teamInProject);
		createBackLog(savedTiP);

		final Set<AppUser> usersFromTeam = userInProjectRepository.findDistinctByTeamInProjectTeamId(teamId).stream()
				.map(UserInProject::getUser).collect(Collectors.toSet());

		usersFromTeam.forEach(x -> addProjectRolesToUser(x, savedTiP, project));

	}

	public List<ProjectLabelSO> getLabels(Long projectId) {
		return projectLabelRepository.findByProjectId(projectId).stream().map(x -> mapper.map(x, ProjectLabelSO.class))
				.collect(Collectors.toList());
	}

	public List<LabelSO> getFilteredLabels(Long projectId, String type, String id) {
		return projectLabelRepository
				.findByProjectIdAndTypeAndIdContainingIgnoreCase(projectId,
						pl.pa3c.agileman.model.label.Type.valueOf(type), id)
				.stream().map(x -> mapper.map(x, LabelSO.class)).collect(Collectors.toList());
	}

	public void removeLabel(Long projectId, String labelId) {
		projectLabelRepository.deleteAllByIdAndProjectId(labelId, projectId);
	}

	public List<BaseProjectTeamSO> getTeams(Long projectId) {
		final List<TeamInProject> tips = teamInProjectRepository.findAllByProjectId(projectId);

		return tips.stream().map(x -> {
			final BaseProjectTeamSO bptSO = mapper.map(x.getTeam(), BaseProjectTeamSO.class);
			bptSO.setType(x.getType().name());
			return bptSO;
		}).collect(Collectors.toList());
	}

	@Transactional
	public void removeTeam(Long projectId, Long teamId) {
		final TeamInProject tip = teamInProjectRepository.findByProjectIdAndTeamId(projectId, teamId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Cannot find project with id: " + projectId + " which contains team with id: " + teamId));
		teamInProjectRepository.delete(tip);
	}

	public ProjectUserRolesInfoSO getTeamProjectUsersRoles(Long projectId, Long teamId) {
		final TeamInProject tip = getTeamInProject(projectId, teamId);

		final List<UserInProject> uips = userInProjectRepository.findAllByTeamInProjectId(tip.getId());

		final ProjectUserRolesInfoSO projectUserRolesInfoSO = new ProjectUserRolesInfoSO();
		projectUserRolesInfoSO.setProjectType(tip.getType().name());

		uips.forEach(x -> projectUserRolesInfoSO.getUsers().add(getProjectUserWithRoles(x)));

		return projectUserRolesInfoSO;
	}

	public MultiRoleBaseUserSO updateProjectUserRoles(Long projectId, Long teamId, String login, List<String> roles) {
		final TeamInProject tip = getTeamInProject(projectId, teamId);
		final UserInProject uip = userInProjectRepository.findByUserIdAndTeamInProjectId(login, tip.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find project with id: " + projectId
						+ " which contains team with id: " + teamId + " and user " + login));

		if (!RoleUtil.getRolesByType(tip.getType().name()).containsAll(roles)) {
			throw new BadRequestException(
					"There are roles that are not valid for type of project " + tip.getType().name());
		}

		roleInProjectRepository.deleteAllByUserInProjectId(uip.getId());
		roles.forEach(x -> roleInProjectRepository.save(new RoleInProject(RoleUtil.toEnum(x), uip)));

		return getProjectUserWithRoles(uip);
	}

	private MultiRoleBaseUserSO getProjectUserWithRoles(UserInProject uip) {
		final List<RoleInProject> rips = roleInProjectRepository.findAllByUserInProjectId(uip.getId());
		final MultiRoleBaseUserSO multiRoleUser = mapper.map(uip.getUser(), MultiRoleBaseUserSO.class);

		multiRoleUser.setRoles(rips.stream().map(rip -> rip.getRole().name()).collect(Collectors.toList()));
		return multiRoleUser;

	}

	private void createBackLog(TeamInProject tip) {
		final TaskContainer taskContainer = new TaskContainer();
		taskContainer.setTeamInProject(tip);
		taskContainer.setTitle("Backlog");
		taskContainer.setType(Type.BACKLOG);
		taskContainerRepository.save(taskContainer);
	}

	private void addProjectRolesToUser(final AppUser user, final TeamInProject teamInProject, final Project project) {
		final RoleInProject roleInProject = new RoleInProject();
		final UserInProject uip = new UserInProject();
		uip.setUser(user);
		uip.setTeamInProject(teamInProject);
		final UserInProject savedUiP = userInProjectRepository.save(uip);

		if (user.getLogin().equals(project.getCreatedBy())) {
			roleInProject.setRole(TeamProjectRole.PROJECT_SUPER_ADMIN);
		} else {
			roleInProject.setRole(TeamProjectRole.PROJECT_BASIC);
		}
		roleInProject.setUserInProject(savedUiP);
		roleInProjectRepository.save(roleInProject);
	}

	private TeamInProject getTeamInProject(Long projectId, Long teamId) {
		return teamInProjectRepository.findByProjectIdAndTeamId(projectId, teamId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Cannot find project with id: " + projectId + " which contains team with id: " + teamId));
	}

	public List<TaskContainerSO> getTeamInProjectTaskContainers(Long projectId, Long teamId) {
		return taskContainerRepository.findAllByTeamInProjectId(getTeamInProject(projectId, teamId).getId()).stream()
				.map(x -> mapper.map(x, TaskContainerSO.class)).collect(Collectors.toList());
	}

}
