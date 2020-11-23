package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.project.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	List<Project> findAllByCreatedBy(String login);

}
