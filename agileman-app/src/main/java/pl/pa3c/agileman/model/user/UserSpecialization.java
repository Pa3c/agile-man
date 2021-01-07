package pl.pa3c.agileman.model.user;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.model.base.LongIdEntity;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserSpecialization extends LongIdEntity {
	@ManyToOne
	private AppUser user;
	@ManyToOne
	private Specialization specialization;
	private Integer skill = 0;
}
