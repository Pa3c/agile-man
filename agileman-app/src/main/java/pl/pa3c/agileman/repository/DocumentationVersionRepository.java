package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.documentation.DocumentationVersion;

public interface DocumentationVersionRepository extends JpaRepository<DocumentationVersion, Long>{
	List<DocumentationVersion> findByDocumentationId(Long id);
}
