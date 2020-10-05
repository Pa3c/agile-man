package pl.pa3c.agileman.api.info;
import static pl.pa3c.agileman.api.info.InformationSI.Constants.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(URL)
public interface InformationSI {

	public static final class Constants{
		public static final String URL = "/info";
        private Constants() {
            super();
        }
	}
	
	@GetMapping
	public InformationSO get();
}
