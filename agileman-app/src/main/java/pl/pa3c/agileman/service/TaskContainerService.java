package pl.pa3c.agileman.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.IdSO;
import pl.pa3c.agileman.api.state.StateSO;
import pl.pa3c.agileman.api.task.FilterSO;
import pl.pa3c.agileman.api.task.RangeProp;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.api.taskcontainer.DetailedTaskContainerSO;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;
import pl.pa3c.agileman.controller.exception.BadRequestException;
import pl.pa3c.agileman.controller.exception.UnconsistentDataException;
import pl.pa3c.agileman.model.project.ProjectType;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.task.Type;
import pl.pa3c.agileman.model.task.UserTask;
import pl.pa3c.agileman.model.taskcontainer.State;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.model.taskcontainer.TaskContainerStatus;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.repository.StateRepository;
import pl.pa3c.agileman.repository.TaskRepository;
import pl.pa3c.agileman.repository.TeamInProjectRepository;
import pl.pa3c.agileman.repository.usertask.UserTaskRepository;

@Service
public class TaskContainerService extends CommonService<Long, TaskContainerSO, TaskContainer> {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserTaskRepository userTaskRepository;

	@Autowired
	private TeamInProjectRepository tipRepository;

	@Autowired
	public TaskContainerService(JpaRepository<TaskContainer, Long> taskContainerRepository) {
		super(taskContainerRepository);
	}

	@Override
	@Transactional
	public TaskContainerSO get(Long id) {
		final TaskContainerSO taskContainerSO = super.get(id);
		final TeamInProject tip = tipRepository.getOne(id);
		final List<State> containerStates = stateRepository.findByTaskContainerId(id);
		final List<Task> containerTasks = taskRepository.findByTaskContainerId(id);
		final Set<StateSO> sortedstates = sortStates(containerStates);
		final Map<String, List<TaskSO>> tasksByState = getTaskBySortedState(containerTasks, containerStates,
				sortedstates);

		final DetailedTaskContainerSO detailedTaskContainerSO = mapper.map(taskContainerSO,
				DetailedTaskContainerSO.class);
		detailedTaskContainerSO.setTeamId(tip.getTeam().getId());
		detailedTaskContainerSO.setProjectId(tip.getProject().getId());
		detailedTaskContainerSO.setStates(sortedstates);
		detailedTaskContainerSO.setTasks(tasksByState);

		return detailedTaskContainerSO;
	}

	@Transactional
	public TaskContainerSO copy(final Long id, TaskContainerSO taskContainerSO) {
		final TaskContainer taskContainerToCopy = findById(id);

		final TaskContainer newInstance = new TaskContainer();
		newInstance.setTeamInProject(taskContainerToCopy.getTeamInProject());
		newInstance.setTitle(taskContainerSO.getTitle());
		newInstance.setOvercontainer(taskContainerToCopy.getOvercontainer());
		newInstance.setType(taskContainerToCopy.getType());

		if (taskContainerToCopy.getTeamInProject().getType() != ProjectType.XP) {
			newInstance.setOvercontainer(null);
		}
		if (taskContainerToCopy.getTeamInProject().getType() != ProjectType.SCRUM) {
			newInstance.setCloseDate(null);
			newInstance.setOpenDate(null);
		} else {
			newInstance.setCloseDate(taskContainerSO.getCloseDate());
			newInstance.setOpenDate(taskContainerSO.getOpenDate());
		}

		final TaskContainer savedNewInstance = repository.save(newInstance);

		final List<State> containerStates = stateRepository.findByTaskContainerId(id);

		containerStates.forEach(x -> {
			final State state = new State();
			state.setName(x.getName());
			state.setOrder(x.getOrder());
			state.setTaskContainer(savedNewInstance);
			stateRepository.save(state);
		});

		return mapper.map(newInstance, TaskContainerSO.class);
	}

	@Transactional
	public TaskContainerSO changeStatus(Long id, String status) {
		final TaskContainer container = findById(id);

		if (status.equalsIgnoreCase(TaskContainerStatus.OPEN.name())) {
			container.setClosed(false);
		} else if (status.equalsIgnoreCase(TaskContainerStatus.CLOSE.name())) {
			container.setClosed(true);
		}

		return mapper.map(container, TaskContainerSO.class);
	}

	@Transactional
	public TaskContainerSO changeStatus(Long id, String status, IdSO<Long> taskContainerId) {
		if (status.equalsIgnoreCase(TaskContainerStatus.OPEN.name())) {
			return changeStatus(id, status);
		} else if (!status.equalsIgnoreCase(TaskContainerStatus.CLOSE.name())) {
			throw new BadRequestException("Invalid status");
		} else if (taskContainerId == null || taskContainerId.getId() == null) {
			throw new BadRequestException("Provide task container body to which move not closed tasks");
		}
		final TaskContainer closedTaskContainer = findById(id);
		closedTaskContainer.setClosed(true);
		final TaskContainer newTaskContainer = findById(taskContainerId.getId());
		taskRepository.findByTaskContainerIdAndClosedIsNull(id).forEach(x -> x.setTaskContainer(newTaskContainer));

		return mapper.map(closedTaskContainer, TaskContainerSO.class);
	}

	public Map<String, List<TaskSO>> filterContainer(Long id, FilterSO filter) {
		final Set<Task> tasks = createFilteredTasksList(filter,taskRepository.findByTaskContainerId(id));
		final List<UserTask> userTask = userTaskRepository.findByTaskTaskContainerIdAndTypeIn(id,
				Arrays.asList(Type.EXECUTOR, Type.OBSERVER));

		filter.getPropUserList().forEach((key, value) -> userTask.forEach(ut -> {
			if (!ut.getType().name().equalsIgnoreCase(key)) {
				return;
			}
			final AppUser user = ut.getUser();
			for (String s : value) {
				if (user.getLogin().contains(s) || user.getName().contains(s) || user.getSurname().contains(s)) {
					tasks.add(ut.getTask());
				}
			}

		}));

		final List<State> containerStates = stateRepository.findByTaskContainerId(id);
		final Set<StateSO> sortedstates = sortStates(containerStates);
		final Map<String, List<TaskSO>> mappedTasks = getTaskBySortedState(tasks, containerStates, sortedstates);

		return mappedTasks;
	}

	private Set<Task> createFilteredTasksList(FilterSO filter, List<Task> containerTasks) {
		final Field[] fields = Task.class.getDeclaredFields();
		final Set<String> fieldset = new HashSet<>();
		fieldset.addAll(filter.getPropDate().keySet());
		fieldset.addAll(filter.getPropNumberRange().keySet());
		fieldset.addAll(filter.getPropUserList().keySet());
		fieldset.addAll(filter.getPropValueList().keySet());
		

		final List<Field> fieldsToCheck = Arrays.asList(fields).stream().filter(x->fieldset.contains(x.getName())).collect(Collectors.toList());
		final Set<Task> tasks = new HashSet<>();
		
		if(fieldset.isEmpty()) {
			tasks.addAll(containerTasks);
			return tasks;
		}
		
		containerTasks.forEach(x -> {
			if (checkIfTaskMeetsFilterReqs(x, filter, fieldsToCheck)) {
				tasks.add(x);
			}
		});
		return tasks;
	}

	private Set<StateSO> sortStates(List<State> containerStates) {
		final Comparator<StateSO> byOrder = Comparator.comparingInt(StateSO::getOrder);
		final Supplier<TreeSet<StateSO>> orderStates = () -> new TreeSet<>(byOrder);
		return containerStates.stream().map(x -> mapper.map(x, StateSO.class))
				.collect(Collectors.toCollection(orderStates));
	}
	

	private Map<String, List<TaskSO>> getTaskBySortedState(Collection<Task> containerTasks, List<State> containerStates,
			Set<StateSO> sortedstates) {

		final Map<String, List<TaskSO>> tasks = new HashMap<>();
		sortedstates.forEach(x -> tasks.putIfAbsent(x.getName(), new ArrayList<>()));

		containerTasks.stream().forEach(x -> {
			if (!tasks.containsKey(x.getState())) {
				x.setState(containerStates.get(0).getName());
				taskRepository.save(x);
			}
			tasks.get(x.getState()).add(mapper.map(x, TaskSO.class));
		});
		return tasks;
	}

	private boolean checkIfTaskMeetsFilterReqs(Task task, FilterSO filter, List<Field> fields) {
		boolean contains = false;
		for (Field f : fields) {
			f.setAccessible(true);
			switch (f.getType().getSimpleName()) {
			case "String":
				contains = checkStringPropContains(f, task, filter.getPropValueList());
				break;
			case "LocalDateTime":
				contains = checkDatePropContains(f, task, filter.getPropDate());
				break;
			case "Integer":
				contains = checkNumberPropContains(f, task, filter.getPropNumberRange());
				break;
			default:
				break;
			}
			f.setAccessible(false);
			if (contains) {
				return true;
			}
		}
		return contains;
	}

	private boolean checkDatePropContains(Field f, Task task, Map<String, LocalDateTime> values) {
		final String fieldName = f.getName().toLowerCase();

		if (!values.containsKey(fieldName)) {
			return true;
		}
		final LocalDateTime tempProp = getFieldValue(f, task);
		if(tempProp==null) {
			return false;
		}
		final LocalDateTime day = values.get(fieldName);
		return day.minusDays(2).isBefore(task.getDeadline()) && day.plusDays(2).isAfter(task.getDeadline());
	}

	private boolean checkNumberPropContains(Field f, Task task, Map<String, RangeProp> values) {
		final String fieldName = f.getName().toLowerCase();

		if (!values.containsKey(fieldName)) {
			return true;
		}
		final Integer tempProp = (Integer) getFieldValue(f, task);
		return values.get(fieldName).getFrom() >= tempProp && values.get(fieldName).getTo() <= tempProp;

	}

	private boolean checkStringPropContains(Field f, Task task, Map<String, List<String>> values) {
		final String fieldName = f.getName().toLowerCase();

		if(!values.containsKey(fieldName)) {
			return false;
		}
		if (values.get(fieldName).isEmpty()) {
			return true;
		}

		String[] tempProp = ((String) getFieldValue(f, task)).split("[,\\s]");

		final List<String> props = values.get(fieldName);
		if (props.isEmpty()) {
			return false;
		}

		boolean contains = false;
		for (String s : tempProp) {
			if(!s.isBlank()){
				contains = props.stream().anyMatch(x -> x.contains(s));
			}
			if (contains) {
				break;
			}
		}

		return contains;
	}

	@SuppressWarnings("unchecked")
	private <T> T getFieldValue(Field f, Task task) {

		try {
			return (T) f.get(task);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new UnconsistentDataException(e.getMessage());
		}
	}
}
