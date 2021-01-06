package pl.pa3c.agileman.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.IdSO;
import pl.pa3c.agileman.api.task.FilterSO;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSI;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.service.CommonService;
import pl.pa3c.agileman.service.TaskContainerService;

@RestController
@CrossOrigin
public class TaskContainerController extends CommonController<Long, TaskContainerSO, TaskContainer> implements TaskContainerSI{

	@Autowired
	public TaskContainerController(CommonService<Long, TaskContainerSO, TaskContainer> commonService) {
		super(commonService);
	}

	@Override
	public TaskContainerSO copy(Long id, TaskContainerSO taskContainerSO) {
		
		return ((TaskContainerService)commonService).copy(id,taskContainerSO);
	}

	@Override
	public TaskContainerSO changeStatus(Long id, String status,IdSO<Long> taskContainerId) {
		
		return ((TaskContainerService)commonService).changeStatus(id,status,taskContainerId);
	}

	@Override
	public  Map<String, List<TaskSO>> filterContainer(Long id, FilterSO filter) {
		
		return ((TaskContainerService)commonService).filterContainer(id,filter);
	}

}
