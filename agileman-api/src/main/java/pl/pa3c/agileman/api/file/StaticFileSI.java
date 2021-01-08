package pl.pa3c.agileman.api.file;

import static pl.pa3c.agileman.api.file.StaticFileSI.Constants.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@RequestMapping(URL)
public interface StaticFileSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Constants {
		public static final String URL = "static_file";
	}

	 @GetMapping("/{fileName:.+}")
	public ResponseEntity<Resource> get(@PathVariable String fileName, HttpServletRequest request);
}
