package pl.pa3c.agileman.api.documentation;

import static pl.pa3c.agileman.api.documentation.DocumentationSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api("Label  Management API")
@RequestMapping(URL)
public interface DocumentationSI {

	final class Constants {
        public static final String URL = "/documentation";
        private Constants() {
            super();
        }

    }
}
