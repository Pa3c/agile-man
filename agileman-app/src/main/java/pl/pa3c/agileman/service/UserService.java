package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.RoleRepository;

@Service
public class UserService extends CommonService<String, UserSO, AppUser>{

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	public UserService(JpaRepository<AppUser, String> userRepository) {
		super(userRepository);
	}
}
