package pl.pa3c.agileman.api.documentation;

import static pl.pa3c.agileman.api.documentation.DocumentationVersionSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Api("Doc version  Management API")
@RequestMapping(URL)
public interface DocumentationVersionSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
		public static final String URL = "/documentationversion";
	}
}
