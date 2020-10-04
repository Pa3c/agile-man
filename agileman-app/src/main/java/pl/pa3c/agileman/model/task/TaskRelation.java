package pl.pa3c.agileman.model.task;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.IdEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskRelation extends IdEntity<Long>{
	
	@ManyToOne
	@JoinColumn(name = "related_task_id")
	private Task relatedTask;
	private String type;
}
