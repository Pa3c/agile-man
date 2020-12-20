package pl.pa3c.agileman.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.pa3c.agileman.model.project.TeamProjectRole;
import pl.pa3c.agileman.model.project.RoleInProject;

public interface RoleInProjectRepository extends JpaRepository<RoleInProject, Long> {

	List<RoleInProject> findAllByUserInProjectId(Long id);

	@Query("select rip from RoleInProject rip where rip.userInProject.id = ?1 AND rip.role IN ?2")
	Optional<RoleInProject> findTeamRole(Long id, List<TeamProjectRole> roles);

	@Transactional
	void deleteByUserInProjectId(Long id);

}
