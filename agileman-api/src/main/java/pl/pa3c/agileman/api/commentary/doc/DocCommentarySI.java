package pl.pa3c.agileman.api.commentary.doc;

import static pl.pa3c.agileman.api.commentary.doc.DocCommentarySI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Api("Documentation commentary  Management API")
@RequestMapping(URL)
public interface DocCommentarySI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
		public static final String URL = "/doccommentary";
	}
}
