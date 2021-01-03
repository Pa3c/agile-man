package pl.pa3c.agileman.api.info;
import static pl.pa3c.agileman.api.info.InformationSI.Constants.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@RequestMapping(URL)
public interface InformationSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Constants{
		public static final String URL = "/info";
	}
	
	@GetMapping
	public InformationSO get();
}
