package pl.pa3c.agileman.api.label;

import static pl.pa3c.agileman.api.label.LabelSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api("Label  Management API")
@RequestMapping(URL)
public interface LabelSI {

	final class Constants {
        public static final String URL = "/label";
        private Constants() {
            super();
        }

    }
}
