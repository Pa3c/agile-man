package pl.pa3c.agileman.api.user;

import static pl.pa3c.agileman.api.user.UserSI.Constants.URL;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pl.pa3c.agileman.api.TitleNameSO;
import pl.pa3c.agileman.api.project.ProjectSO;

@Api("User Management API")
@RequestMapping(URL)
public interface UserSI {

	final class Constants {
		public static final String URL = "/user";
		private Constants() {
			super();
		}
	}

	@ApiOperation(value = "Return teams of user")
	@GetMapping(path = "/{login}/team", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<UserTeamSO> teamsOfUser(@PathVariable String login);

	@ApiOperation(value = "Return projects of user")
	@GetMapping(path = "/{login}/project", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<ProjectSO> projectsOfUser(@PathVariable String login);
	
	@ApiOperation(value = "Return teams from user project")
	@GetMapping(path = "/{login}/project/{id}/team", produces = MediaType.APPLICATION_JSON_VALUE)
	List<TitleNameSO<Long>> teamsOfUser(@PathVariable String login,@PathVariable Long id);
	
	@ApiOperation(value = "Return teams from user project")
	@GetMapping(path = "/{login}/project/{project_id}/team/{team_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	DetailedUserProjectSO detailedUserTeamProject(@PathVariable String login,@PathVariable("project_id") Long projectId,@PathVariable("team_id") Long teamId);
	
	@ApiOperation(value = "Return basic info of user")
	@GetMapping(path = "/{login}/basic", produces = MediaType.APPLICATION_JSON_VALUE)
	BaseUserSO getBasicInfo(@PathVariable String login);
	
	@ApiOperation(value = "Return basic filtered info of users")
	@GetMapping(path = "basic/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
	List<BaseUserSO> getFilteredBasicInfo(@RequestParam String login);
		
	
	
	
}
