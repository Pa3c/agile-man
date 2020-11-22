package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.project.RoleInProject;

public interface RoleInProjectRepository extends JpaRepository<RoleInProject, Long>{

	List<RoleInProject> findAllByUserInProjectId(Long id);

}
