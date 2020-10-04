package pl.pa3c.agileman.model.taskcontainer;

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
public class State extends IdEntity<Long>{
	private String text;
	private Integer order;
	
	@ManyToOne
	@JoinColumn(name = "task_container_id")
	private TaskContainer taskContainer;
}
