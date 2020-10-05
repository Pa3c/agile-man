package pl.pa3c.agileman.api.project;

import static pl.pa3c.agileman.api.project.ProjectSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api("Project Management API")
@RequestMapping(URL)
public interface ProjectSI {

	final class Constants {
        public static final String URL = "/project";
        private Constants() {
            super();
        }

    }
}
