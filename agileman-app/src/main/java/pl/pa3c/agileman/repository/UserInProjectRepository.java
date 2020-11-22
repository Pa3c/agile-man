package pl.pa3c.agileman.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.project.UserInProject;

public interface UserInProjectRepository extends JpaRepository<UserInProject, Long> {

	List<UserInProject> findByUserId(String login);

	Set<UserInProject> findAllByUserIdAndTeamInProjectProjectId(String login, Long id);

	Optional<UserInProject> findByUserIdAndTeamInProjectId(String login, Long id);

	List<UserInProject> findDistinctByTeamInProjectTeamId(Long teamId);

}
