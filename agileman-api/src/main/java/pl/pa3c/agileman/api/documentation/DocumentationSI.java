package pl.pa3c.agileman.api.documentation;

import static pl.pa3c.agileman.api.documentation.DocumentationSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Api("Label  Management API")
@RequestMapping(URL)
public interface DocumentationSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
        public static final String URL = "/documentation";
    }
}
