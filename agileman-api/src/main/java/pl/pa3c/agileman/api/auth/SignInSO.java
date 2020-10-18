package pl.pa3c.agileman.api.auth;

import lombok.Data;

@Data
public class SignInSO {
	private String login;
	private String password;
}
