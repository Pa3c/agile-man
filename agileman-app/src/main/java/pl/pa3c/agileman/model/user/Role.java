package pl.pa3c.agileman.model.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.BaseEntity;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.task.Type;
import pl.pa3c.agileman.model.task.UserTaskInProjectTeam;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity<String>{
	
}
