package pl.pa3c.agileman.api.commentary.task;

import static pl.pa3c.agileman.api.commentary.task.TaskCommentarySI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Api("Task commentary  Management API")
@RequestMapping(URL)
public interface TaskCommentarySI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
		public static final String URL = "/taskcommentary";
	}
}
