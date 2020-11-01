package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.project.UserInProject;

public interface UserInProjectRepository extends JpaRepository<UserInProject, Long>{

	List<UserInProject> findByUserId(String login);

}
