package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.documentation.Documentation;

public interface DocumentationRepository extends JpaRepository<Documentation, Long>{

	List<Documentation> findByProjectId(Long projectId);

}
