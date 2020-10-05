package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.task.TaskSI;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class TaskController extends CommonController<Long, TaskSO, Task> implements TaskSI{

	@Autowired
	public TaskController(CommonService<Long, TaskSO, Task> commonService) {
		super(commonService);
	}

}
