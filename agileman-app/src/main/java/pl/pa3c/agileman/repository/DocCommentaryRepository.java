package pl.pa3c.agileman.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.commentary.DocumentationCommentary;

public interface DocCommentaryRepository extends JpaRepository<DocumentationCommentary, Long>{
	Collection<DocumentationCommentary> findAllByDocumentationId(Long resourceId);
}
