package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pa3c.agileman.model.User;

public interface UserRepository extends JpaRepository<User,String> {
}
