package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.taskcontainer.TaskContainerSI;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class TaskContainerController extends CommonController<Long, TaskContainerSO, TaskContainer> implements TaskContainerSI{

	@Autowired
	public TaskContainerController(CommonService<Long, TaskContainerSO, TaskContainer> commonService) {
		super(commonService);
	}

}
