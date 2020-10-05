package pl.pa3c.agileman.model.taskcontainer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.BaseEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskContainer extends BaseEntity<Long>{
	private String title;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="overcontainer_id")
	private TaskContainer overcontainer;
}
