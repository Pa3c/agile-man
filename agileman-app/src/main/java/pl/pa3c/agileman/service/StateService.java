package pl.pa3c.agileman.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.state.StateSO;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.taskcontainer.State;
import pl.pa3c.agileman.repository.TaskRepository;

@Service
public class StateService extends CommonService<Long, StateSO, State>{

	@Autowired
	private TaskRepository taskRepostiory;

	@Autowired
	public StateService(JpaRepository<State, Long> stateRepository) {
		super(stateRepository);
	}

	@Transactional
	public StateSO updateName(Long id, StateSO stateSO) {
		stateSO.setName(stateSO.getName().trim());
		StateSO state = super.update(id, stateSO);
		List<Task> tasks = taskRepostiory.findByTaskContainerIdAndState(stateSO.getTaskContainerId(),stateSO.getOldName().trim());
		tasks.forEach(x->x.setState(state.getName()));
		return state;
	}

	@Transactional
	public List<StateSO> updateAll(List<StateSO> entitySO) {
		List<StateSO> updatedList = new ArrayList<>();
		entitySO.forEach(x->updatedList.add(super.update(x.getId(), x)));
		return updatedList;
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		final State state = repository.getOne(id);
		final Long containerId = state.getTaskContainer().getId();
		taskRepostiory.deleteAllByTaskContainerIdAndState(containerId,state.getName());
		super.delete(id);
		
	}

}
