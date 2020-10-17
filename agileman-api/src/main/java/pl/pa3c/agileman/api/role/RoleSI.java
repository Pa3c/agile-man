package pl.pa3c.agileman.api.role;

import static pl.pa3c.agileman.api.role.RoleSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api("Label  Management API")
@RequestMapping(URL)
public interface RoleSI {

	final class Constants {
        public static final String URL = "/role";
        private Constants() {
            super();
        }

    }
}
