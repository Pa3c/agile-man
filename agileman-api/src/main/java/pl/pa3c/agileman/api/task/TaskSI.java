package pl.pa3c.agileman.api.task;

import static pl.pa3c.agileman.api.task.TaskSI.Constants.URL;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Api("Task  Management API")
@RequestMapping(URL)
public interface TaskSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
		public static final String URL = "/task";
	}

	@ApiOperation(value = "Return users of task")
	@GetMapping(path = "/{id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
	List<TaskUserSO> getTaskUsers(@PathVariable("id") Long id);

	@ApiOperation(value = "Add user to task")
	@PutMapping(path = "/{id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
	void addTaskUsers(@PathVariable("id") Long id, @RequestBody TaskUserSO taskUserSO);
	
	//POSSIBLE STATUS OPEN REPONE
	@ApiOperation(value = "Add user to task")
	@PutMapping(path = "/{id}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	TaskSO setStatus(@PathVariable("id") Long id,@PathVariable("status") String status);

	@ApiOperation(value = "Remove user from task")
	@DeleteMapping(path = "/{id}/user/{login}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	void removeTaskUser(@PathVariable("id") Long id, @PathVariable("login") String login,
			@PathVariable("type") String type);
	
	@ApiOperation(value = "Move task to the other container")
	@PutMapping(path = "/{id}/taskcontainer/{containerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	TaskSO move(@PathVariable("id") Long id,@PathVariable("containerId") Long containerId);	

	@ApiOperation(value = "Copy task to the other container")
	@PostMapping(path = "/{id}/taskcontainer/{containerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	TaskSO copy(@PathVariable("id") Long id,@PathVariable("containerId") Long containerId);	

}
