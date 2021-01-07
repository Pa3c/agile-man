package pl.pa3c.agileman.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.user.UserSpecialization;

public interface UserSpecializationRepository extends JpaRepository<UserSpecialization, Long>{
	List<UserSpecialization> findByUserId(String id);

	@Transactional
	void deleteBySpecializationIdAndUserId(String name, String login);

	UserSpecialization findBySpecializationIdAndUserId(String name, String login);
}
