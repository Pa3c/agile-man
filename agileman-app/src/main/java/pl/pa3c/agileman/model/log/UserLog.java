package pl.pa3c.agileman.model.log;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.user.AppUser;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class UserLog extends BaseLog{
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser user;
}
