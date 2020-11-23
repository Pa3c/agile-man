package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.team.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	List<Team> findAllByCreatedBy(String login);

}
