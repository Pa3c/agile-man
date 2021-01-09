package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.commentary.file.DocumentationFileInfo;

public interface DocumentationFileRepository extends JpaRepository<DocumentationFileInfo, Long>{

}
