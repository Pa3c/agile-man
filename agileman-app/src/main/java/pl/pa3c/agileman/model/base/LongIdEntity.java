package pl.pa3c.agileman.model.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class LongIdEntity implements IdEntity<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	protected Long id;
}
