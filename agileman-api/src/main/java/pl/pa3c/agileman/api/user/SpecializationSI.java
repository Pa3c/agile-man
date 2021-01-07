package pl.pa3c.agileman.api.user;

import static pl.pa3c.agileman.api.user.SpecializationSI.Constants.URL;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.IdSO;

@Api("Task Container Management API")
@RequestMapping(URL)
public interface SpecializationSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
		public static final String URL = "/specialization";
	}
	
	@ApiOperation(value = "Get filtered specializations")
	@GetMapping(path = "basic/filter",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	List<IdSO<String>> filterSpecializations(@RequestParam String name);	

	@ApiOperation(value = "Create specialization of user")
	@PostMapping(path = "/{name}/user/{login}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	UserSpecializationSO addUserSpecialization(@PathVariable String name,@PathVariable String login,
			@RequestBody UserSpecializationSO specialization);
	
	@ApiOperation(value = "Update specialization of user")
	@PutMapping(path = "/{name}/user/{login}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	UserSpecializationSO updateUserSpecialization(@PathVariable String name,@PathVariable String login,
			@RequestBody UserSpecializationSO specialization);
	
	@ApiOperation(value = "Delete specialization of user")
	@DeleteMapping(path = "/{name}/user/{login}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	void deleteUserSpecialization(@PathVariable String name,@PathVariable String login);
}
