package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.auth.AuthSI;
import pl.pa3c.agileman.api.auth.SignInSO;
import pl.pa3c.agileman.api.auth.SignUpSO;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.security.SecurityConstants;
import pl.pa3c.agileman.security.UserCreds;
import pl.pa3c.agileman.service.TokenService;
import pl.pa3c.agileman.service.UserService;

@RestController
@CrossOrigin
public class AuthController implements AuthSI {

	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;

	@Override
	public ResponseEntity<?> signin(SignInSO signInSO) {
		tokenService.authenticate(signInSO.getLogin(), signInSO.getPassword());
		AppUser loginUser = userService.findById(signInSO.getLogin());
		UserCreds userCreds = new UserCreds(loginUser, userService.findUserRoles(signInSO.getLogin()));
		return buildResponse(loginUser,tokenService.generateToken(userCreds));
	}

	@Override
	public UserSO registerUser(SignUpSO signUpSO) {
		return userService.register(signUpSO);
	}
	
	private ResponseEntity<?> buildResponse(AppUser loginUser, String token) {
		return ResponseEntity.ok().header(SecurityConstants.JWT_TOKEN_HEADER,token).body(loginUser);
	}

}
