package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.user.UserInProject;

public interface UserInProjectRepository extends JpaRepository<UserInProject, Long>{

	List<UserInProject> findAllByUserId(String login);

}
