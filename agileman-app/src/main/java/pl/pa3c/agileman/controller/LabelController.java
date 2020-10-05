package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.label.LabelSI;
import pl.pa3c.agileman.api.label.LabelSO;
import pl.pa3c.agileman.model.label.Label;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class LabelController extends CommonController<String, LabelSO, Label> implements LabelSI{

	@Autowired
	public LabelController(CommonService<String, LabelSO, Label> commonService) {
		super(commonService);
	}

}
