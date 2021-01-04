package pl.pa3c.agileman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.task.TaskSI;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.api.task.TaskUserSO;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.service.CommonService;
import pl.pa3c.agileman.service.TaskService;

@RestController
@CrossOrigin
public class TaskController extends CommonController<Long, TaskSO, Task> implements TaskSI {

	@Autowired
	public TaskController(CommonService<Long, TaskSO, Task> commonService) {
		super(commonService);
	}

	@Override
	public List<TaskUserSO> getTaskUsers(Long id) {
		return ((TaskService) commonService).getTaskUsers(id);
	}

	@Override
	public void addTaskUsers(Long id, TaskUserSO taskUserSO) {
		((TaskService) commonService).addTaskUser(id, taskUserSO);
	}

	@Override
	public void removeTaskUser(Long id, String login, String type) {
		((TaskService) commonService).removeTaskUser(id, login, type);
	}

	@Override
	public TaskSO setStatus(Long id, String status) {
		return ((TaskService) commonService).setStatus(id, status);
	}

	@Override
	public TaskSO move(Long id, Long containerId) {
		return ((TaskService) commonService).move(id, containerId);
	}

	@Override
	public TaskSO copy(Long id, Long containerId) {
		return ((TaskService) commonService).copy(id, containerId);
	}

}
