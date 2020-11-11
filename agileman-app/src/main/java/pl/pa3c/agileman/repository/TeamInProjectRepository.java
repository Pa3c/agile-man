package pl.pa3c.agileman.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.team.Team;

public interface TeamInProjectRepository extends JpaRepository<TeamInProject, Long>{

	Optional<TeamInProject> findByProjectIdAndTeamId(Long projectId, Long teamId);

}
