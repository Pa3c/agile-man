package pl.pa3c.agileman.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.commentary.CommentarySO;
import pl.pa3c.agileman.api.commentary.DocIdCommentary;
import pl.pa3c.agileman.controller.exception.BadRequestException;
import pl.pa3c.agileman.model.commentary.DocumentationCommentary;
import pl.pa3c.agileman.repository.DocCommentaryRepository;

@Service
public class DocCommentaryService extends CommonService<Long, CommentarySO, DocumentationCommentary>{
	@Autowired
	public DocCommentaryService(JpaRepository<DocumentationCommentary, Long> repository) {
		super(repository);
	}

	public List<CommentarySO> getAllByResource(Long resourceId) {
		
		return ((DocCommentaryRepository)repository).findAllByDocumentationId(resourceId)
				.stream().map(x->mapper.map(x,CommentarySO.class)).collect(Collectors.toList());
	}
	
	@Override
	public CommentarySO create(CommentarySO entitySO) {
		DocIdCommentary com = new DocIdCommentary(entitySO);
		
		if(com.getResourceId()==null) {
			throw new BadRequestException("Cannot add not reference comment");
		}
		return super.create(com);
	}

}
