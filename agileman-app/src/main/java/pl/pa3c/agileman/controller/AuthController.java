package pl.pa3c.agileman.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;


import pl.pa3c.agileman.api.auth.AuthSI;
import pl.pa3c.agileman.api.auth.SignInSO;
import pl.pa3c.agileman.api.auth.SignUpSO;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.security.AppUserDetails;
import pl.pa3c.agileman.security.SecurityConstants;
import pl.pa3c.agileman.service.TokenService;
import pl.pa3c.agileman.service.UserService;

@RestController
public class AuthController implements AuthSI {

	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	
	@Override
	public ResponseEntity<?> signin(SignInSO signInSO) {
			tokenService.authenticate(signInSO.getLogin(), signInSO.getPassword());
	        AppUser loginUser = userService.findById(signInSO.getLogin());
	        AppUserDetails details = new AppUserDetails(loginUser);
	        HttpHeaders jwtHeader = getJwtHeader(details);
	        return new ResponseEntity<>(loginUser, jwtHeader, OK);
	}

	@Override
	public UserSO registerUser(SignUpSO signUpSO) {
		return userService.register(signUpSO);
	}
	
    private HttpHeaders getJwtHeader(AppUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.JWT_TOKEN_HEADER, tokenService.generateToken(userDetails));
        return headers;
    }

}
