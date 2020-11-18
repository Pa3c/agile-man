package pl.pa3c.agileman.model.project;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.model.base.IdEntity;
import pl.pa3c.agileman.model.base.LongIdEntity;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleInProject extends LongIdEntity {
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "role")
	private ProjectRole role;
}
