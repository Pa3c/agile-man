package pl.pa3c.agileman.api.user;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.api.taskcontainer.TaskContainerSO;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class DetailedUserProjectSO extends ProjectSO {
	private Long teamInProjectId;
	private String type;
	private Set<TaskContainerSO> taskContainers;
	private Set<String> roles;
}
