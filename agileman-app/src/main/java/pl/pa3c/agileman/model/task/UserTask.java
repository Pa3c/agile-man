package pl.pa3c.agileman.model.task;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.LongIdEntity;
import pl.pa3c.agileman.model.user.AppUser;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class UserTask extends LongIdEntity{

	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser user;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;
}
