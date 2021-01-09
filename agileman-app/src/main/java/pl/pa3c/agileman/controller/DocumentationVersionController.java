package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.documentation.DocumentationVersionSI;
import pl.pa3c.agileman.api.documentation.DocumentationVersionSO;
import pl.pa3c.agileman.model.documentation.DocumentationVersion;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class DocumentationVersionController extends CommonController<Long, DocumentationVersionSO, DocumentationVersion> implements DocumentationVersionSI{

	@Autowired
	public DocumentationVersionController(CommonService<Long, DocumentationVersionSO, DocumentationVersion> commonService) {
		super(commonService);
	}
}
