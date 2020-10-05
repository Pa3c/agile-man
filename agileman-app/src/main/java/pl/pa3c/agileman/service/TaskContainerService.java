package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;

@Service
public class TaskContainerService extends CommonService<Long, TaskContainerSO, TaskContainer>{


	@Autowired
	public TaskContainerService(JpaRepository<TaskContainer, Long> taskContainerRepository) {
		super(taskContainerRepository);
	}

}
