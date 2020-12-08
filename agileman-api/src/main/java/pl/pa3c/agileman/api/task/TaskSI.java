package pl.pa3c.agileman.api.task;

import static pl.pa3c.agileman.api.task.TaskSI.Constants.URL;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Task  Management API")
@RequestMapping(URL)
public interface TaskSI {

	final class Constants {
		public static final String URL = "/task";

		private Constants() {
			super();
		}

	}

	@ApiOperation(value = "Return users of task")
	@GetMapping(path = "/{id}/user/", produces = MediaType.APPLICATION_JSON_VALUE)
	List<TaskUserSO> getTaskUsers(@PathVariable("id") Long id);

	@ApiOperation(value = "Add user to task")
	@PutMapping(path = "/{id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
	void addTaskUsers(@PathVariable("id") Long id, @RequestBody TaskUserSO taskUserSO);

	@ApiOperation(value = "Remove user from task")
	@DeleteMapping(path = "/{id}/user/{login}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	void removeTaskUser(@PathVariable("id") Long id, @PathVariable("login") String login,
			@PathVariable("type") String type);

}
