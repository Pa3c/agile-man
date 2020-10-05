package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.documentation.DocumentationSO;
import pl.pa3c.agileman.model.documentation.Documentation;

@Service
public class DocumentationService extends CommonService<Long, DocumentationSO, Documentation>{


	@Autowired
	public DocumentationService(JpaRepository<Documentation, Long> documentationRepository) {
		super(documentationRepository);
	}

}
