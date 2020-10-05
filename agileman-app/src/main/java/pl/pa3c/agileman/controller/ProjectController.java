package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.project.ProjectSI;
import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.model.project.Project;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class ProjectController extends CommonController<Long, ProjectSO, Project> implements ProjectSI{

	@Autowired
	public ProjectController(CommonService<Long, ProjectSO, Project> commonService) {
		super(commonService);
	}

}
