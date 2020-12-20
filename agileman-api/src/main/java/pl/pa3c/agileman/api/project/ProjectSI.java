package pl.pa3c.agileman.api.project;

import static pl.pa3c.agileman.api.project.ProjectSI.Constants.URL;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.pa3c.agileman.api.label.LabelSO;
import pl.pa3c.agileman.api.user.MultiRoleBaseUserSO;

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
	@ApiResponses(value = { 
			@ApiResponse(code = 409, message = "Project has got alredy that team"),
			@ApiResponse(code = 200, message = "Added team to project"),
			@ApiResponse(code = 404, message = "If project or team not exists"),
			})
	@PutMapping(path = "/{project_id}/team/{team_id}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	void addTeam(@PathVariable(name = "project_id") Long projectId,
			@PathVariable(name = "team_id") Long teamId, @PathVariable(name = "type") String type);
	
	@ApiOperation(value = "Add team to project")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Added teamF to project"),
			@ApiResponse(code = 404, message = "If project or team not exists"),
			})
	@GetMapping(path = "/{project_id}/team", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	List<BaseProjectTeamSO> getTeams(@PathVariable(name = "project_id") Long projectId);
	
	@ApiOperation(value = "Add labels to project")
	@ApiResponses(value = { 
			@ApiResponse(code = 409, message = "Project has got alredy that label"),
			@ApiResponse(code = 200, message = "Added team to project"), 
			@ApiResponse(code = 404, message = "If project not exists"), 
			})
	@PutMapping(path = "/{project_id}/label", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	void addLabels(@PathVariable(name = "project_id") Long projectId,@RequestBody List<LabelSO> labels);
	
	@ApiOperation(value = "Remvoe label from project")
	@ApiResponses(value = { 
			@ApiResponse(code = 202, message = "Label removed from project"), 
			@ApiResponse(code = 404, message = "If label or project not exists"), 
			})
	@DeleteMapping(path = "/{project_id}/label/{label_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	void removeLabel(@PathVariable(name = "project_id") Long projectId,@PathVariable(name = "label_id") String labelId);
	
	
	@ApiOperation(value = "Remvoe label from project")
	@ApiResponses(value = { 
			@ApiResponse(code = 202, message = "Team removed from project"), 
			@ApiResponse(code = 404, message = "If team or project not exists"), 
			})
	@DeleteMapping(path = "/{project_id}/team/{team_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	void removeTeam(@PathVariable(name = "project_id") Long projectId,@PathVariable(name = "team_id") Long teamId);
	
	@ApiOperation(value = "Add labels to project")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Project labels returned successfully"), 
			@ApiResponse(code = 404, message = "If project not exists"), 
			})
	@GetMapping(path = "/{project_id}/label", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	List<ProjectLabelSO> getLabels(@PathVariable(name = "project_id") Long projectId);
	
	
	@ApiOperation(value = "Add labels to project")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Project labels returned successfully"), 
			@ApiResponse(code = 404, message = "If project not exists"), 
			})
	@GetMapping(path = "/{project_id}/label/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	List<LabelSO> getFilteredLabels(@PathVariable(name = "project_id") Long projectId,
			@RequestParam("type")String type,@RequestParam("filter")String value);
	
	
	@ApiOperation(value = "Get project team users with project roles")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Returned successful"), 
			@ApiResponse(code = 404, message = "Any project do not have specific team"), 
			})
	@GetMapping(path = "/{project_id}/team/{team_id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	ProjectUserRolesInfoSO getTeamProjectUsersRoles(@PathVariable(name = "project_id") Long projectId,
			@PathVariable("team_id")Long teamId);

}
