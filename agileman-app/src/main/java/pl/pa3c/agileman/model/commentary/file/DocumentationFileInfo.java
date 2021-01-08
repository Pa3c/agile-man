package pl.pa3c.agileman.model.commentary.file;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.documentation.Documentation;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class DocumentationFileInfo extends FileInfo {

	@ManyToOne
	private Documentation documentation;

}
