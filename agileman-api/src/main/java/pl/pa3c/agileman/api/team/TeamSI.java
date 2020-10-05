package pl.pa3c.agileman.api.team;

import static pl.pa3c.agileman.api.team.TeamSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api("Team Management API")
@RequestMapping(URL)
public interface TeamSI {

	final class Constants {
        public static final String URL = "/team";
        private Constants() {
            super();
        }

    }
}
