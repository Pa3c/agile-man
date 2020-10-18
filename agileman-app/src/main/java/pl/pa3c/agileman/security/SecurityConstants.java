package pl.pa3c.agileman.security;

public final class SecurityConstants {
	private SecurityConstants() {
	}
	public static final String SECRET = "my-default-secret";
	public static final String TOKEN_HEADER = "Bearer";
	public static final String HTTP_OPTIONS = "OPTIONS";
	public static final String AUTHORITIES = "Authorities";
	public static final String JWT_TOKEN_HEADER = "Jwt-Token";	
	public static final int EXPIRATION_TIME = 86400000; // one day in millis
	public static final String[] PUBLIC_URLS = {"/auth/signin","/auth/signup","/swagger*/**","/webjars/**","/docs/**","/v2/api-docs*"};
}
