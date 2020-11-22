package pl.pa3c.agileman.api.project;

import static pl.pa3c.agileman.api.project.ProjectSI.Constants.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Project Management API")
@RequestMapping(URL)
public interface ProjectSI {

	final class Constants {
        public static final String URL = "/project";
        private Constants() {
            super();
        }

    }
	
	@ApiOperation(value = "Add team to project")
	@ApiResponses(value = { @ApiResponse(code = 409, message = "Project has got alredy that team"),
			@ApiResponse(code = 200, message = "Added team to project") })
	@GetMapping(path = "/{project_id}/team/{team_id}/type/{type}",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	void addTeamToProject(
			@PathVariable(name = "project_id") Long projectId,
	@PathVariable(name = "team_id")Long teamId,
	@PathVariable(name = "type")String type);
}
