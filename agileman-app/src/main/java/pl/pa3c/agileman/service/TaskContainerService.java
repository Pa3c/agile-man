package pl.pa3c.agileman.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.state.StateSO;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.api.taskcontainer.DetailedTaskContainerSO;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;
import pl.pa3c.agileman.model.project.TeamInProject;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.taskcontainer.State;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.repository.StateRepository;
import pl.pa3c.agileman.repository.TaskRepository;
import pl.pa3c.agileman.repository.TeamInProjectRepository;

@Service
public class TaskContainerService extends CommonService<Long, TaskContainerSO, TaskContainer> {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private TaskRepository taskRepository;
	
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
		final DetailedTaskContainerSO detailedTaskContainerSO = mapper.map(taskContainerSO,
				DetailedTaskContainerSO.class);

		final TeamInProject tip = tipRepository.getOne(taskContainerSO.getTeamInProjectId());
		detailedTaskContainerSO.setTeamId(tip.getTeam().getId());
		detailedTaskContainerSO.setProjectId(tip.getProject().getId());

		final List<State> containerStates = stateRepository.findByTaskContainerId(taskContainerSO.getId());
		final List<Task> containerTasks = taskRepository.findByTaskContainerId(taskContainerSO.getId());

		final Comparator<StateSO> byOrder = Comparator.comparingInt(StateSO::getOrder);
		final Supplier<TreeSet<StateSO>> orderStates = () -> new TreeSet<>(byOrder);

		detailedTaskContainerSO.setStates(containerStates.stream().map(x -> mapper.map(x, StateSO.class))
				.collect(Collectors.toCollection(orderStates)));
		final Map<String, List<TaskSO>> tasks = new HashMap<>();

		detailedTaskContainerSO.getStates().forEach(x -> tasks.putIfAbsent(x.getName(), new ArrayList<>()));

		containerTasks.stream().forEach(x -> {
			if (!tasks.containsKey(x.getState())) {
				x.setState(containerStates.get(0).getName());
				taskRepository.save(x);
			}
			tasks.get(x.getState()).add(mapper.map(x, TaskSO.class));
		});
		detailedTaskContainerSO.setTasks(tasks);

		return detailedTaskContainerSO;
	}

}
