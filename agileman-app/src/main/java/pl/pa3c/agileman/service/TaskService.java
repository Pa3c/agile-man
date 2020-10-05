package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.model.task.Task;

@Service
public class TaskService extends CommonService<Long, TaskSO, Task>{


	@Autowired
	public TaskService(JpaRepository<Task, Long> taskRepository) {
		super(taskRepository);
	}

}
