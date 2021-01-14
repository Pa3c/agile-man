package pl.pa3c.agileman.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.documentation.DocumentationVersionSO;
import pl.pa3c.agileman.controller.exception.NotSupportedOperationException;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.model.documentation.Documentation;
import pl.pa3c.agileman.model.documentation.DocumentationVersion;
import pl.pa3c.agileman.repository.DocumentationRepository;

@Service
public class DocumentationVersionService extends CommonService<Long, DocumentationVersionSO, DocumentationVersion> {

	@Autowired
	private DocumentationRepository documentationRepository;

	@Autowired
	public DocumentationVersionService(JpaRepository<DocumentationVersion, Long> repository) {
		super(repository);
	}

	@Override
	@Transactional
	public DocumentationVersionSO create(DocumentationVersionSO entitySO) {
		final Documentation doc = findResourceById(entitySO.getResourceId());
		final DocumentationVersion docToSave = mapper.map(entitySO, classEntity);
		docToSave.setDocumentation(doc);
		final DocumentationVersion docVersion = super.save(docToSave);
		return mapper.map(docVersion, classSo);
	}

	@Override
	public DocumentationVersionSO update(Long id, DocumentationVersionSO entitySO) {
		throw new NotSupportedOperationException("Cannot edit any documentation version. It can be only created");
	}

	private Documentation findResourceById(Long id) {
		return documentationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No documentation of id " + id));
	}

}
