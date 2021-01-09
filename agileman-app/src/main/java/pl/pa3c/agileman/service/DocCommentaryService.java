package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.commentary.CommentarySO;
import pl.pa3c.agileman.model.commentary.DocumentationCommentary;

@Service
public class DocCommentaryService extends CommonService<Long, CommentarySO, DocumentationCommentary>{


	@Autowired
	public DocCommentaryService(JpaRepository<DocumentationCommentary, Long> repository) {
		super(repository);
	}

}
