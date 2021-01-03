package pl.pa3c.agileman.api.role;

import static pl.pa3c.agileman.api.role.ProjectRoleSI.Constants.URL;

import java.util.List;

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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Api("Project Role  Management API")
@RequestMapping(URL)
public interface ProjectRoleSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
        public static final String URL = "/projectrole";
    }
	
	@ApiOperation(value = "Get roles by project type")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Roles of project type found"),
			@ApiResponse(code = 404, message = "Roles of project type not found"),
			})
	@GetMapping(path = "/type/{project_type}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	List<RoleSO> getByProjectType(@PathVariable("project_type")String projectType);
	
}
