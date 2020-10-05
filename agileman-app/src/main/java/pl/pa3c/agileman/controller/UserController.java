package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.user.UserSI;
import pl.pa3c.agileman.api.user.UserSO;
import pl.pa3c.agileman.model.user.AppUser;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class UserController extends CommonController<String, UserSO, AppUser> implements UserSI{

	@Autowired
	public UserController(CommonService<String, UserSO, AppUser> commonService) {
		super(commonService);
	}

}
