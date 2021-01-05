package pl.pa3c.agileman.api.taskcontainer;

import static pl.pa3c.agileman.api.taskcontainer.TaskContainerSI.Constants.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public interface TaskContainerSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
        public static final String URL = "/taskcontainer";
    }
	
	
	@ApiOperation(value = "Copy taskcontainer with new dates (for scrum project based on team in projectId)")
	@PostMapping(path = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	TaskContainerSO copy(@PathVariable("id") Long id,@RequestBody TaskContainerSO taskContainerSO);	
	
	@ApiOperation(value = "Change status of container status param is OPEN or CLOSED")
	@PostMapping(path = "/{id}/status",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	TaskContainerSO changeStatus(@PathVariable("id") Long id,@RequestParam("status") String status,@RequestBody(required = false) IdSO<Long> taskContainerId);
}
