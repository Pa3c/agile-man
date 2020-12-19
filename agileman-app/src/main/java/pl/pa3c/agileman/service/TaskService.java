package pl.pa3c.agileman.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.task.StepSO;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.api.task.TaskUserSO;
import pl.pa3c.agileman.model.task.Step;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.task.Type;
import pl.pa3c.agileman.model.task.UserTask;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.StepRepository;
import pl.pa3c.agileman.repository.user.UserRepository;
import pl.pa3c.agileman.repository.usertask.ITaskUserInfo;
import pl.pa3c.agileman.repository.usertask.UserTaskRepository;

@Service
public class TaskService extends CommonService<Long, TaskSO, Task> {

	@Autowired
	private StepRepository stepRepository;

	@Autowired
	private UserTaskRepository utRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public TaskService(JpaRepository<Task, Long> taskRepository) {
		super(taskRepository);
	}

	@Override
	@Transactional
	public TaskSO create(TaskSO entitySO) {
		final TaskSO so = super.create(entitySO);

		entitySO.getSteps().forEach(x -> {
			x.setTaskId(so.getId());
			Step step = stepRepository.save(mapper.map(x, Step.class));
			so.getSteps().add(mapper.map(step, StepSO.class));
		});
		return so;
	}

	@Override
	public TaskSO get(Long id) {
		final TaskSO taskSO = super.get(id);
		final List<StepSO> steps = stepRepository.findAllByTaskId(id).stream().map(x -> mapper.map(x, StepSO.class))
				.collect(Collectors.toList());
		taskSO.setSteps(steps);
		return taskSO;
	}

	@Override
	public TaskSO update(Long id, TaskSO entitySO) {

		if (entitySO.getSteps() == null) {
			return super.update(id, entitySO);
		}
		stepRepository.deleteAllByTaskId(id);
		final List<Step> savedSteps = stepRepository
				.saveAll(entitySO.getSteps().stream().map(x -> mapper.map(x, Step.class)).collect(Collectors.toList()));
		final TaskSO returnedSO = super.update(id, entitySO);
		returnedSO.setSteps(savedSteps.stream().map(x -> mapper.map(x, StepSO.class)).collect(Collectors.toList()));

		return returnedSO;

	}

	public List<TaskUserSO> getTaskUsers(Long id) {
		final List<ITaskUserInfo> taskUserInfo = utRepository.getTaskBasicUserInfo(id);
		return taskUserInfo.stream()
				.map(x -> new TaskUserSO(x.getId(), x.getName(), x.getSurname(), x.getType().toString()))
				.collect(Collectors.toList());
	}

	@Transactional
	public void addTaskUser(Long id, TaskUserSO taskUserSO) {
		final Task task = repository.getOne(id);
		final AppUser user = userRepository.getOne(taskUserSO.getId());
		final Type relationType = Type.valueOf(taskUserSO.getType());

		final UserTask userTask = new UserTask();
		userTask.setTask(task);
		userTask.setUser(user);
		userTask.setType(relationType);

		utRepository.save(userTask);
	}

	@Transactional
	public void removeTaskUser(Long id, String login, String type) {

		utRepository.deleteByTaskIdAndUserIdAndType(id, login, Type.valueOf(type));
	}

}
