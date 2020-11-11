package pl.pa3c.agileman.model.project;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.IdEntity;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.model.team.Team;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamInProject extends IdEntity<Long> {

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ProjectType type;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "teamInProject")
	private Set<TaskContainer> taskContainers;

}
