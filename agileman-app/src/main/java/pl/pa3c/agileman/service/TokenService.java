package pl.pa3c.agileman.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import pl.pa3c.agileman.model.auth.AppUserDetails;

@Service
@Slf4j
public class TokenService {
	
	private static final String SECRET = "my-default-secret";
	private static final int EXPIRATION_TIME = 86400000; // one day in millis

	public String generateToken(Authentication authentication) {

		final AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
		final Instant currentTime = ZonedDateTime.now(ZoneId.systemDefault()).toInstant();
		return Jwts.builder()
				.setSubject((appUserDetails.getUsername()))
				.setIssuedAt(Date.from(currentTime))
				.setExpiration(new Date(currentTime.toEpochMilli() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}
//
//	public String getUserNameFromJwtToken(String token) {
//		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
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
}
