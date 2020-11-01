package pl.pa3c.agileman.service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Arrays.stream;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
		return Jwts.builder()
				.setSubject((userCreds.getUsername()))
				.setIssuedAt(Date.from(currentTime))
				.setExpiration(Date.from(currentTime.plusMillis(SecurityConstants.EXPIRATION_TIME)))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.claim(SecurityConstants.AUTHORITIES, claims)
				.compact();
	}

	public List<GrantedAuthority> getAuthorities(String token) {
		List<String> claims = getTokenClaims(token);
		return claims.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);
		userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return userPasswordAuthToken;
	}

	private String[] getUserClaims(UserCreds user) {
		return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(size -> new String[size]);
	}

	private List<String> getTokenClaims(String token) {
		 Jws<Claims> claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
		return claims.getBody().get(SecurityConstants.AUTHORITIES, List.class);
	}
	
    public void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(authToken);
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

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody().getSubject();
	}

}
