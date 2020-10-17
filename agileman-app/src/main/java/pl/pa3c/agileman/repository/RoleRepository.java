package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.user.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

}
