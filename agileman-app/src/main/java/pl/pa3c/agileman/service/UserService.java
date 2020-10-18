package pl.pa3c.agileman.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.auth.SignUpSO;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.model.user.UserRole;
import pl.pa3c.agileman.repository.RoleRepository;
import pl.pa3c.agileman.repository.UserRoleRepository;
import pl.pa3c.agileman.security.AppUserDetails;

@Service
@Transactional
public class UserService extends CommonService<String, UserSO, AppUser> implements UserDetailsService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(JpaRepository<AppUser, String> userRepository) {
		super(userRepository);
	}

	public UserSO register(SignUpSO signUpSO) {
		AppUser user = modelMapper.map(signUpSO, AppUser.class);
		user.setPassword(passwordEncoder.encode(signUpSO.getPassword()));
		user = commonRepository.save(user);
		UserRole role = new UserRole(user,roleRepository.getOne("COMMON"));
		role.setId(userRoleRepository.count()+1);
		userRoleRepository.save(role);
		return modelMapper.map(commonRepository.getOne(user.getLogin()), UserSO.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		return new AppUserDetails(findById(username),userRoleRepository.findByUserId(username));
	}
	
	public List<UserRole> findUserRoles(String username){
		return userRoleRepository.findByUserId(username);
	}
}
