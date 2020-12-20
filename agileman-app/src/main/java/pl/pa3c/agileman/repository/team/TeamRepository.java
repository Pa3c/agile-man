package pl.pa3c.agileman.repository.team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.pa3c.agileman.model.team.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	List<Team> findAllByCreatedBy(String login);
	
	@Query("SELECT t.id as id,t.title as title FROM Team t WHERE t.id = :id"
			+ " OR t.title LIKE CONCAT('%',:value,'%')"
			+ " OR t.description LIKE CONCAT('%',:value,'%')")	
	List<IBasicTeamInfo> getFilteredBasicTeam(@Param("id")Long id, @Param("value")String value);
}
