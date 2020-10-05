package pl.pa3c.agileman.api.user;

import static pl.pa3c.agileman.api.user.UserSI.Constants.URL;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Api("User Management API")
@RequestMapping(URL)
public interface UserSI {

	final class Constants {
        public static final String URL = "/user";
        private Constants() {
            super();
        }

    }
}
