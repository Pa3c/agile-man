package pl.pa3c.agileman.model.documentation;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.BaseEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Documentation extends BaseEntity<Long>{
	private String title;
	private String content;
	
}
