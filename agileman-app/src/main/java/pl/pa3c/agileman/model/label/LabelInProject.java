package pl.pa3c.agileman.model.label;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.LongIdEntity;
import pl.pa3c.agileman.model.user.AppUser;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class LabelInProject extends LongIdEntity {
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="project_id")
	private AppUser project;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="label_id")
	private Label label;
}
