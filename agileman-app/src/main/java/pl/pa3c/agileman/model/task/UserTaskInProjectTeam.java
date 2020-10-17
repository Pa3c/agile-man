package pl.pa3c.agileman.model.task;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.IdEntity;
import pl.pa3c.agileman.model.user.UserInProject;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class UserTaskInProjectTeam extends IdEntity<Long>{

	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	
	@ManyToOne
	@JoinColumn(name = "user_in_project_id")
	private UserInProject userInProject;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;
}
