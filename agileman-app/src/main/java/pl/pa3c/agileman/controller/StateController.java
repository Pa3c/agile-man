package pl.pa3c.agileman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.state.StateSI;
import pl.pa3c.agileman.api.state.StateSO;
import pl.pa3c.agileman.model.taskcontainer.State;
import pl.pa3c.agileman.service.CommonService;
import pl.pa3c.agileman.service.StateService;

@RestController
@CrossOrigin
public class StateController extends CommonController<Long, StateSO, State> implements StateSI {

	@Autowired
	public StateController(CommonService<Long, StateSO, State> commonService) {
		super(commonService);
	}

	@Override
	public StateSO updateName(Long id, StateSO stateSO) {

		return ((StateService) commonService).updateName(id, stateSO);
	}

	@Override
	public List<StateSO> updateAll(List<StateSO> entitySO) {
		return ((StateService) commonService).updateAll(entitySO);
	}

}
