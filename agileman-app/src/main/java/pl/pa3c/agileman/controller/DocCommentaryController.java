package pl.pa3c.agileman.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.commentary.CommentarySO;
import pl.pa3c.agileman.api.commentary.doc.DocCommentarySI;
import pl.pa3c.agileman.model.commentary.DocumentationCommentary;
import pl.pa3c.agileman.service.CommonService;
import pl.pa3c.agileman.service.DocCommentaryService;

@RestController
@CrossOrigin
public class DocCommentaryController extends CommonController<Long, CommentarySO, DocumentationCommentary> implements DocCommentarySI {

	public DocCommentaryController(CommonService<Long, CommentarySO, DocumentationCommentary> commonService) {
		super(commonService);
	}

	@Override
	public List<CommentarySO> getAllByResource(Long resourceId) {
		
		return ((DocCommentaryService)commonService).getAllByResource(resourceId);
	}

}
