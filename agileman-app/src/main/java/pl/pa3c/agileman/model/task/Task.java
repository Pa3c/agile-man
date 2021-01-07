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
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.model.base.BaseLongEntity;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Task extends BaseLongEntity {

	public Task(Task task) {
		this.closed = task.closed;
		this.complexity = task.complexity;
		this.createdBy = task.createdBy;
		this.creationDate = task.creationDate;
		this.deadline = task.deadline;
		this.description = task.description;
		this.id = task.id;
		this.labels = task.labels;
		this.likes = task.likes;
		this.majority = task.majority;
		this.modificationDate = task.modificationDate;
		this.modifiedBy = task.modifiedBy;
		this.projectId = task.projectId;
		this.reopened = task.reopened;
		this.solution = task.solution;
		this.state = task.state;
		this.storyPoints = task.storyPoints;
		this.taskContainer = task.taskContainer;
		this.technologies = task.technologies;
		this.title = task.title;
		this.type = task.type;
	}

	private String title;
	private String description;
	private Integer storyPoints;
	private LocalDateTime deadline;
	private LocalDateTime closed;
	private LocalDateTime reopened;

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

	// added due to issues with passing project id values in admin
	private Long projectId;

	@ManyToOne
	@JoinColumn(name = "task_container_id")
	private TaskContainer taskContainer;
//	
//	@PrePersist
//	void preUpdateMethod() {
//		this.state = "DUPA";
//	}
//	
//	@PostUpdate
//	void postUpdateMethod() {
//		this.state = "zupa";
//	}
}
