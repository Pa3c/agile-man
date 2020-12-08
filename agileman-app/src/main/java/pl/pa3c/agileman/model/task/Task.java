package pl.pa3c.agileman.model.task;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Task extends BaseLongEntity{

	private String title;
	private String description;
	private Integer storyPoints;
	private LocalDateTime deadline;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TaskType type;
	private Integer likes = 0;
	private Integer majority;
	private Integer complexity;
	private String state;
	private String solution;
	private String labels;
	private String technologies;
	
	//added due to issues with passing project id values in admin
	private Long projectId;
	
	@ManyToOne
	@JoinColumn(name = "task_container_id")
	private TaskContainer taskContainer;
}
