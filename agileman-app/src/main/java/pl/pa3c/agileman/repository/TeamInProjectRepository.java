package pl.pa3c.agileman.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.project.TeamInProject;

public interface TeamInProjectRepository extends JpaRepository<TeamInProject, Long> {

	Optional<TeamInProject> findByProjectIdAndTeamId(Long projectId, Long teamId);

	List<TeamInProject> findAllByTeamId(Long id);

	List<TeamInProject> findAllByProjectId(Long id);

	@Transactional
	void deleteByProjectIdAndTeamId(Long projectId, Long teamId);

}
