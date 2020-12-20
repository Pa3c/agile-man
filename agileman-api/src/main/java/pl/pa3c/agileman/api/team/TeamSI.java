package pl.pa3c.agileman.api.team;

import static pl.pa3c.agileman.api.team.TeamSI.Constants.URL;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pl.pa3c.agileman.api.user.RoleBaseUserSO;

@Api("Team Management API")
@RequestMapping(URL)
public interface TeamSI {

	final class Constants {
        public static final String URL = "/team";
        private Constants() {
            super();
        }

    }
	
	
	@ApiOperation(value = "Set user to team")
	@PostMapping(path = "/user",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public TeamWithUsersSO createTeamWithUsers(@RequestBody TeamWithUsersSO teamWithUsersSO);
	
	@ApiOperation(value = "Get users of team")
	@GetMapping(path = "/{id}/user",produces = MediaType.APPLICATION_JSON_VALUE)
	public TeamWithUsersSO getTeamWithUsers(@PathVariable Long id);
	
	@ApiOperation(value = "Get users of team")
	@PostMapping(path = "/{id}/user",produces = MediaType.APPLICATION_JSON_VALUE)
	public RoleBaseUserSO addUserToTeam(@PathVariable Long id,@RequestBody RoleBaseUserSO roleBaseUserSO);
}
