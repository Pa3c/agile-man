package pl.pa3c.agileman.model.documentation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentationVersion extends BaseLongEntity{
	private String content;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="documentation_id")
	private Documentation documentation;
}
