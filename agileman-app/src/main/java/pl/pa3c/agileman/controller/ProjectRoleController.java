package pl.pa3c.agileman.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.role.ProjectRoleSI;
import pl.pa3c.agileman.api.role.RoleSO;
import pl.pa3c.agileman.model.project.RoleUtil;

@RestController
@CrossOrigin
public class ProjectRoleController implements ProjectRoleSI{

	@Override
	public List<RoleSO> getByProjectType(String projectType) {
		return RoleUtil.getRolesByType(projectType).stream().map(RoleSO::new).collect(Collectors.toList());
	}

}
