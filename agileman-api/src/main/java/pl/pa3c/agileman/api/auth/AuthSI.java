package pl.pa3c.agileman.api.auth;

import static pl.pa3c.agileman.api.auth.AuthSI.Constants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.pa3c.agileman.api.user.UserSO;

@RequestMapping(URL)
public interface AuthSI {

	final class Constants {
		public static final String URL = "/auth";
		public static final String SIGN_IN = "/signin";
		public static final String SIGN_UP = "/signup";

		private Constants() {
			super();
		}

	}

	@PostMapping(SIGN_IN)
	public ResponseEntity<?> signin(@RequestBody SignInSO signInSO);

	@PostMapping(SIGN_UP)
	UserSO registerUser(@RequestBody SignUpSO signUpSO);
}
