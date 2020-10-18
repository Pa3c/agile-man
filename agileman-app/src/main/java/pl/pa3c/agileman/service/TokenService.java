package pl.pa3c.agileman.service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import pl.pa3c.agileman.security.AppUserDetails;
import pl.pa3c.agileman.security.SecurityConstants;

@Service
@Slf4j
public class TokenService {

	@Autowired
	private AuthenticationManager authenticationManager;

	public String generateToken(AppUserDetails appUserDetails) {
		final Instant currentTime = ZonedDateTime.now(ZoneId.systemDefault()).toInstant();
		String[] claims = getUserClaims(appUserDetails);
		return JWT.create().withIssuedAt(Date.from(currentTime)).withSubject(appUserDetails.getUsername())
				.withArrayClaim(SecurityConstants.AUTHORITIES, claims)
				.withExpiresAt(new Date(currentTime.toEpochMilli() + SecurityConstants.EXPIRATION_TIME))
				.sign(HMAC512(SecurityConstants.SECRET));
	}

	public Authentication authenticate(String login, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login, password));
		setAuthenticationContext(authentication);
		return authentication;
	}
	
    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPasswordAuthToken = new
                UsernamePasswordAuthenticationToken(username, null, authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

	public String getSubject(String token) {
		return Jwts.parser().setSigningKey(token).parseClaimsJws(token).getBody().getSubject();
	}

	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = getTokenClaims(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	private void setAuthenticationContext(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public List<String> getAuthorities(AppUserDetails appUserDetails) {
		return appUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	private String[] getUserClaims(AppUserDetails user) {
		return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(size -> new String[size]);
	}

	private String[] getTokenClaims(String token) {
		JWTVerifier verifier = JWT.require(HMAC512(SecurityConstants.SECRET)).build();
		return verifier.verify(token).getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
	}

}
