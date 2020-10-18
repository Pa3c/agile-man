package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.user.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	List<UserRole> findByUserId(String id);
}
