package pl.pa3c.agileman.api.documentation;

import static pl.pa3c.agileman.api.documentation.DocumentationSI.Constants.URL;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Api("Doc  Management API")
@RequestMapping(URL)
public interface DocumentationSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
		public static final String URL = "/documentation";
	}

	@ApiOperation(value = "Return documentatiions of project")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<DocumentationSO> getAllByResource(@RequestParam Long projectId);

	@ApiOperation(value = "Return documentatiion versions")
	@GetMapping(path = "{docId}/version", produces = MediaType.APPLICATION_JSON_VALUE)
	List<DocumentationVersionSO> getAllVersions(@PathVariable(name = "docId") Long docId);
}
