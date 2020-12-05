package pl.pa3c.agileman.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.task.StepSO;
import pl.pa3c.agileman.api.task.TaskSO;
import pl.pa3c.agileman.model.task.Step;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.repository.StepRepository;

@Service
public class TaskService extends CommonService<Long, TaskSO, Task> {

	@Autowired
	private StepRepository stepRepository;

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

}
