package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.auth.AuthSI;
import pl.pa3c.agileman.api.auth.SignInSO;
import pl.pa3c.agileman.api.auth.SignUpSO;
import pl.pa3c.agileman.service.TokenService;
import pl.pa3c.agileman.service.UserService;

@RestController
public class AuthController implements AuthSI{
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private TokenService tokenService;
	
	@Override
	public ResponseEntity<?> signin(SignInSO signInSO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> registerUser(SignUpSO signUpSO) {
		// TODO Auto-generated method stub
		return null;
	}

}
