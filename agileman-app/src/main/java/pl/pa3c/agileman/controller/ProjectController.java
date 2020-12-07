package pl.pa3c.agileman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.label.LabelSO;
import pl.pa3c.agileman.api.project.ProjectLabelSO;
import pl.pa3c.agileman.api.project.ProjectSI;
import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.model.project.Project;
import pl.pa3c.agileman.service.CommonService;
import pl.pa3c.agileman.service.ProjectService;

@RestController
@CrossOrigin
public class ProjectController extends CommonController<Long, ProjectSO, Project> implements ProjectSI{

	@Autowired
	public ProjectController(CommonService<Long, ProjectSO, Project> commonService) {
		super(commonService);
	}

	@Override
	public void addTeam(Long projectId, Long teamId, String type) {
		((ProjectService)commonService).addTeam(projectId, teamId,type);
	}

	@Override
	public void addLabels(Long projectId, List<LabelSO> labels) {
		((ProjectService)commonService).addLabels(projectId, labels);
	}

	@Override
	public List<ProjectLabelSO> getLabels(Long projectId) {
		return ((ProjectService)commonService).getLabels(projectId);
	}
}
