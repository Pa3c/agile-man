package pl.pa3c.agileman.api.user;

import static pl.pa3c.agileman.api.user.UserSI.Constants.URL;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.api.team.TeamSO;

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
	public List<TeamSO> teamsOfUser(@PathVariable String login);

	@ApiOperation(value = "Return projects of user")
	@GetMapping(path = "/{login}/project", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProjectSO> projectsOfUser(@PathVariable String login);
}
