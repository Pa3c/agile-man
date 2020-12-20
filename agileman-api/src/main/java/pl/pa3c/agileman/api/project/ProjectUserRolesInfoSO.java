package pl.pa3c.agileman.api.project;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import pl.pa3c.agileman.api.user.MultiRoleBaseUserSO;

@Data
public class ProjectUserRolesInfoSO {
	private String projectType;
	private List<MultiRoleBaseUserSO> users = new ArrayList<>();
}
