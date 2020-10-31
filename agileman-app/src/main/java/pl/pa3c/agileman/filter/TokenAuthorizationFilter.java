package pl.pa3c.agileman.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import pl.pa3c.agileman.security.SecurityConstants;
import pl.pa3c.agileman.service.TokenService;

@Component
public class TokenAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(SecurityConstants.HTTP_OPTIONS)) {
            response.setStatus(OK.value());
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.BEARER_HEADER)) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authorizationHeader.substring(SecurityConstants.BEARER_HEADER.length());
            if (tokenService.validateJwtToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            	String username = tokenService.getUserNameFromJwtToken(token);
                List<GrantedAuthority> authorities = tokenService.getAuthorities(token);
                Authentication authentication = tokenService.getAuthentication(username, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
	}

}
