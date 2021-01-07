package pl.pa3c.agileman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.IdSO;
import pl.pa3c.agileman.api.user.SpecializationSI;
import pl.pa3c.agileman.api.user.UserSpecializationSO;
import pl.pa3c.agileman.model.user.Specialization;
import pl.pa3c.agileman.service.CommonService;
import pl.pa3c.agileman.service.SpecializationService;

@RestController
@CrossOrigin
public class SpecializationController extends CommonController<String, UserSpecializationSO, Specialization>
		implements SpecializationSI {

	@Autowired
	public SpecializationController(CommonService<String, UserSpecializationSO, Specialization> commonService) {
		super(commonService);
	}

	@Override
	public UserSpecializationSO addUserSpecialization(String name, String login,
			UserSpecializationSO specialization) {

		return ((SpecializationService) commonService).addUserSpecialization(name, login, specialization);
	}

	@Override
	public UserSpecializationSO updateUserSpecialization(String name, String login,
			UserSpecializationSO specialization) {
		return ((SpecializationService) commonService).updateUserSpecialization(name, login, specialization);
	}

	@Override
	public void deleteUserSpecialization(String name, String login) {
		((SpecializationService) commonService).deleteUserSpecialization(name, login);
	}

	@Override
	public List<IdSO<String>> filterSpecializations(String name) {
		return ((SpecializationService) commonService).filterSpecializations(name);
	}

}
