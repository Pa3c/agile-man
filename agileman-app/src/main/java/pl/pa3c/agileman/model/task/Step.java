package pl.pa3c.agileman.model.task;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Step extends BaseLongEntity{
	
	private String description;
	private Boolean done = false;
	@Column(name="step_order")
	private Integer order;
	
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="overstep_id")
	private Step overstep;	
}
