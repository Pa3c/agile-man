package pl.pa3c.agileman.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pl.pa3c.agileman.api.task.StepSO;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.api.task.TaskUserSO;
import pl.pa3c.agileman.controller.exception.BadRequestException;
import pl.pa3c.agileman.controller.exception.ConflictException;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.events.TaskUpdateEvent;
import pl.pa3c.agileman.model.task.Step;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.task.Type;
import pl.pa3c.agileman.model.task.UserTask;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.StepRepository;
import pl.pa3c.agileman.repository.TaskContainerRepository;
import pl.pa3c.agileman.repository.user.UserRepository;
import pl.pa3c.agileman.repository.usertask.ITaskUserInfo;
import pl.pa3c.agileman.repository.usertask.UserTaskRepository;

@Service
@Slf4j
public class TaskService extends CommonService<Long, TaskSO, Task> {

	@Autowired
	private StepRepository stepRepository;

	@Autowired
	private UserTaskRepository utRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskContainerRepository tkRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	public TaskService(JpaRepository<Task, Long> taskRepository) {
		super(taskRepository);
	}

	@Override
	@Transactional
	public TaskSO create(TaskSO entitySO) {

		if (getTaskContainer(entitySO.getTaskContainerId()).getClosed().booleanValue()) {
			throw new ConflictException("You cannot create task in closed container ");
		}

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
	@Transactional
	public TaskSO update(Long id, TaskSO entitySO) {
		final Task task = findById(id);
		publishChange(task);

		if (getTaskContainer(entitySO.getTaskContainerId()).getClosed().booleanValue()) {
			throw new ConflictException("You cannot create task in closed container ");
		}

		if (entitySO.getSteps() == null || entitySO.getSteps().isEmpty()) {
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
		final Task task = findById(id);
		final AppUser user = userRepository.getOne(taskUserSO.getId());
		final Type relationType = Type.valueOf(taskUserSO.getType());

		if (relationType.equals(Type.LIKER)) {
			task.setLikes(task.getLikes() + 1);
		}
		if (relationType.equals(Type.DISLIKER)) {
			task.setLikes(task.getLikes() - 1);
		}

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

	@Transactional
	public TaskSO setStatus(Long id, String status) {
		final Task task = findById(id);
		publishChange(task);
		status = status.toUpperCase();

		if (status.equals("REOPEN")) {
			task.setClosed(null);
			task.setReopened(LocalDateTime.now());
		} else if (status.equals("CLOSE")) {
			task.setClosed(LocalDateTime.now());
		} else {
			throw new BadRequestException("Please provide VALID STATUS. Possible options are REPOPEN AND CLOSE ");
		}
		return mapper.map(task, TaskSO.class);
	}

	@Transactional
	public TaskSO move(Long id, Long containerId) {
		final Task task = findById(id);
		publishChange(task);

		final TaskContainer tk = tkRepository.findById(containerId)
				.orElseThrow(() -> new ResourceNotFoundException("Container with id " + id + " not found"));
		task.setTaskContainer(tk);

		return mapper.map(task, TaskSO.class);
	}

	@Transactional
	public TaskSO copy(Long id, Long containerId) {
		final TaskSO task = get(id);
		publishChange(findById(containerId));

		final TaskContainer tk = tkRepository.findById(containerId)
				.orElseThrow(() -> new ResourceNotFoundException("Container with id " + id + " not found"));

		task.setId(null);
		task.getSteps().forEach(x -> x.setId(null));
		task.setTaskContainerId(tk.getId());

		return create(task);
	}

	private void publishChange(Task task) {
		publisher.publishEvent(new TaskUpdateEvent(task, new Task(task)));
	}


	private TaskContainer getTaskContainer(Long id) {
		return tkRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("There is no task container with id" + id));
	}

}
