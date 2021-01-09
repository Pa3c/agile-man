package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.commentary.DocumentationCommentary;

public interface DocCommentaryRepository extends JpaRepository<DocumentationCommentary, Long>{

}
