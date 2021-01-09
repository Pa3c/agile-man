package pl.pa3c.agileman.model.commentary;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.documentation.Documentation;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentationCommentary extends BaseCommentary{
	@ManyToOne
	@JoinColumn(name = "documentation_id")
	private Documentation documentation;
}
