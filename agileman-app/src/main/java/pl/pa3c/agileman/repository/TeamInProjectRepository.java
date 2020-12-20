package pl.pa3c.agileman.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.project.TeamInProject;

public interface TeamInProjectRepository extends JpaRepository<TeamInProject, Long> {

	Optional<TeamInProject> findByProjectIdAndTeamId(Long projectId, Long teamId);

	Set<TeamInProject> findAllByTeamId(Long id);

}
