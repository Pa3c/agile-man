package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.role.RoleSO;
import pl.pa3c.agileman.model.user.Role;

@Service
public class RoleService extends CommonService<String, RoleSO, Role>{


	@Autowired
	public RoleService(JpaRepository<Role, String> userRepository) {
		super(userRepository);
	}

}
