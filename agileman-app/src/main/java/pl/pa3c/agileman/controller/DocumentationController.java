package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.documentation.DocumentationSI;
import pl.pa3c.agileman.api.documentation.DocumentationSO;
import pl.pa3c.agileman.model.documentation.Documentation;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class DocumentationController extends CommonController<Long, DocumentationSO, Documentation> implements DocumentationSI{

	@Autowired
	public DocumentationController(CommonService<Long, DocumentationSO, Documentation> commonService) {
		super(commonService);
	}

}
