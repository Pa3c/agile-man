package pl.pa3c.agileman.api.info;
import static pl.pa3c.agileman.api.info.InformationEndpoint.Constants.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(InformationEndpoint.Constants.URL)
public interface InformationEndpoint {

	public static final class Constants{
		private static final String VERSION = "/v1";
		private static final String NAME = "/info";
		public static final String URL = VERSION + NAME;
	}
	
	@GetMapping
	public InformationSO get();
}
