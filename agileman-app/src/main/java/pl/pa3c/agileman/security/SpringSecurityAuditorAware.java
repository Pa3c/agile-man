package pl.pa3c.agileman.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	private static final String ANONYMOUS_USER = "anonymousUser";
	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		if(authentication.getPrincipal().equals(ANONYMOUS_USER)) {
			return Optional.of(ANONYMOUS_USER);
		}

		return Optional.of(((UserCreds) authentication.getPrincipal()).getUsername());
	}
}