package pl.pa3c.agileman.model.task;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.BaseEntity;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Task extends BaseEntity<Long>{

	private String title;
	private String description;
	private Integer storyPoints;
	private LocalDateTime deadline;
	private String type;
	private Integer likes;
	private String components;
	private Integer majority;
	private Integer complexity;
	private String state;
	private String solution;
	private String labels;
	private String technologies;
	
	@ManyToOne
	@JoinColumn(name = "task_container_id")
	private TaskContainer taskContainer;
}