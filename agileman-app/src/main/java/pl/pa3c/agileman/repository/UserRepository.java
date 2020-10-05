package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.user.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String>{

}
