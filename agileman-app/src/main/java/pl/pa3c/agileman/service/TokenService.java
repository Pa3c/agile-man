package pl.pa3c.agileman.service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.extern.slf4j.Slf4j;
import pl.pa3c.agileman.security.SecurityConstants;
import pl.pa3c.agileman.security.UserCreds;

@Service
@Slf4j
public class TokenService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String generateToken(UserCreds userCreds) {
		final Instant currentTime = ZonedDateTime.now(ZoneId.systemDefault()).toInstant();
		String[] claims = getUserClaims(userCreds);
		return JWT.create().withIssuedAt(Date.from(currentTime))
				.withIssuer("auth0")
				.withSubject(userCreds.getUsername())
				.withArrayClaim(SecurityConstants.AUTHORITIES, claims)
				.withExpiresAt(Date.from(currentTime.plusSeconds(SecurityConstants.EXPIRATION_TIME)))
				.sign(HMAC512(Base64.getEncoder().encode(SecurityConstants.SECRET.getBytes())));
	}

	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = getTokenClaims(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);
		userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return userPasswordAuthToken;
	}

	public boolean isTokenValid(String username, String token) {
		return !username.isEmpty() && !isTokenExpired(buildVerifier(), token);
	}

	private JWTVerifier buildVerifier() {
		return JWT.require(HMAC512(SecurityConstants.SECRET)).withIssuer("auth0").build();
	}

	public String getSubject(String token) {
		return buildVerifier().verify(token).getSubject();
	}

	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

	public List<String> getAuthorities(UserCreds appUserDetails) {
		return appUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	private String[] getUserClaims(UserCreds user) {
		return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(size -> new String[size]);
	}

	private String[] getTokenClaims(String token) {
		return buildVerifier().verify(token).getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
	}
	
    public void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
