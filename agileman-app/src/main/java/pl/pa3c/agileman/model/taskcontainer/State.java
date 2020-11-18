package pl.pa3c.agileman.model.taskcontainer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.LongIdEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class State extends LongIdEntity{
	private String name;
	@Column(name="state_order")
	private Integer order;
	
	@ManyToOne
	@JoinColumn(name = "task_container_id")
	private TaskContainer taskContainer;

}
