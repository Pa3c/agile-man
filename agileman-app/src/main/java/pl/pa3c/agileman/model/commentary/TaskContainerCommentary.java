package pl.pa3c.agileman.model.commentary;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.taskcontainer.TaskContainer;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskContainerCommentary extends BaseCommentary{
	
	@ManyToOne
	@JoinColumn(name = "task_container_id")
	private TaskContainer taskContainer;
}