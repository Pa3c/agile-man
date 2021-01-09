package pl.pa3c.agileman.api.commentary.task;

import static pl.pa3c.agileman.api.commentary.task.TaskCommentarySI.Constants.URL;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.commentary.CommentarySO;

@Api("Task commentary  Management API")
@RequestMapping(URL)
public interface TaskCommentarySI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
		public static final String URL = "/taskcommentary";
	}
	
	
	@ApiOperation(value = "Return commentary of task")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<CommentarySO> getAllByResource(@RequestParam Long resourceId);
}
