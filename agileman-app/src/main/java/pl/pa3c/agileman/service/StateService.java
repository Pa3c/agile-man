package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.taskcontainer.StateSO;
import pl.pa3c.agileman.model.taskcontainer.State;

@Service
public class StateService extends CommonService<Long, StateSO, State>{


	@Autowired
	public StateService(JpaRepository<State, Long> stateRepository) {
		super(stateRepository);
	}

}
