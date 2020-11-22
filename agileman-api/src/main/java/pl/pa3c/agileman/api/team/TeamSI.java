package pl.pa3c.agileman.api.team;

import static pl.pa3c.agileman.api.team.TeamSI.Constants.URL;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	@GetMapping(path = "/{id}/user/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
	public TeamWithUsersSO addUserToTeam(@PathVariable Long id,@PathVariable String login);
}
