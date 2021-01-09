package pl.pa3c.agileman.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.documentation.DocumentationVersion;

public interface DocumentationVersionRepository extends JpaRepository<DocumentationVersion, Long>{
	Collection<DocumentationVersion> findByDocumentationId(Long id);
}
