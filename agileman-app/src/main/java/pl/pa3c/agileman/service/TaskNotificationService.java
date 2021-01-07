package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.repository.TaskRepository;
import pl.pa3c.agileman.repository.usertask.UserTaskRepository;

@Service
public class TaskNotificationService {

	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserTaskRepository userTaskRepository;
	
	
	
}
