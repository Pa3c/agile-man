package pl.pa3c.agileman.api.task;

import static pl.pa3c.agileman.api.task.TaskSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api("Task  Management API")
@RequestMapping(URL)
public interface TaskSI {

	final class Constants {
        public static final String URL = "/task";
        private Constants() {
            super();
        }

    }
}
