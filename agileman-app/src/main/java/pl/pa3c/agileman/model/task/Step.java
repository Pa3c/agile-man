package pl.pa3c.agileman.model.task;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.IdEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Step extends IdEntity<Long>{
	
	private String description;
	private boolean done;
	
	@Version
	private int version;
	
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="overstep_id")
	private Step overstep;
}
