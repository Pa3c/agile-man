package pl.pa3c.agileman.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.model.user.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreds implements UserDetails {

	private String username;
	private String email;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserCreds(AppUser user, List<UserRole> roles) {
		this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole().getId()))
				.collect(Collectors.toList());
		this.username = user.getLogin();
		this.email = user.getEmail();
		this.password = user.getPassword();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
