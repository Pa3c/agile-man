package pl.pa3c.agileman.api.taskcontainer;

import static pl.pa3c.agileman.api.taskcontainer.TaskContainerSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api("Task Container Management API")
@RequestMapping(URL)
public interface TaskContainerSI {

	final class Constants {
        public static final String URL = "/taskcontainer";
        private Constants() {
            super();
        }

    }
}
