package pl.pa3c.agileman.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.pa3c.agileman.model.user.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String> {

	@Query("SELECT u.id as id,u.name as name,u.surname as surname FROM AppUser u WHERE u.id = ?1")
	IBasicUserInfo getBasicInfo(String id);

	@Query("SELECT u.id as id,u.name as name,u.surname as surname FROM AppUser u WHERE u.id LIKE CONCAT('%',:login,'%')")	
	List<IBasicUserInfo> getFilteredBasicInfo(@Param("login")String login);
}

