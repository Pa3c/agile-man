package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.auth.SignUpSO;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.RoleRepository;
import pl.pa3c.agileman.security.AppUserDetails;

@Service
public class UserService extends CommonService<String, UserSO, AppUser> implements UserDetailsService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(JpaRepository<AppUser, String> userRepository) {
		super(userRepository);
	}

	public UserSO register(SignUpSO signUpSO) {
		AppUser user = modelMapper.map(signUpSO, AppUser.class);
		user.setPassword(passwordEncoder.encode(signUpSO.getPassword()));
		user.getRoles().add(roleRepository.getOne("COMMON"));
		return modelMapper.map(commonRepository.save(user), UserSO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new AppUserDetails(findById(username));
	}
}
