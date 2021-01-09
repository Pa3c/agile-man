package pl.pa3c.agileman.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.documentation.DocumentationSO;
import pl.pa3c.agileman.api.documentation.DocumentationVersionSO;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.model.documentation.Documentation;
import pl.pa3c.agileman.model.project.Project;
import pl.pa3c.agileman.repository.DocumentationRepository;
import pl.pa3c.agileman.repository.DocumentationVersionRepository;
import pl.pa3c.agileman.repository.ProjectRepository;

@Service
public class DocumentationService extends CommonService<Long, DocumentationSO, Documentation> {
	@Autowired
	private DocumentationVersionRepository documentationVersionRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	public DocumentationService(JpaRepository<Documentation, Long> repository) {
		super(repository);
	}

	@Override
	@Transactional
	public DocumentationSO create(DocumentationSO entitySO) {
		final Project p = projectRepository.findById(entitySO.getResourceId())
				.orElseThrow(() -> new ResourceNotFoundException("No project of id " + entitySO.getResourceId()));
		final Documentation doc = super.save(mapper.map(entitySO, classEntity));
		doc.setProject(p);
		return mapper.map(doc, classSo);
	}

	public List<DocumentationSO> getAllByResource(Long projectId) {
		return ((DocumentationRepository) repository).findByProjectId(projectId).stream()
				.map(x -> mapper.map(x, DocumentationSO.class)).collect(Collectors.toList());
	}

	public List<DocumentationVersionSO> getAllVersions(Long id) {
		return documentationVersionRepository.findByDocumentationId(id).stream()
				.map(x -> mapper.map(x, DocumentationVersionSO.class)).collect(Collectors.toList());
	}
}
