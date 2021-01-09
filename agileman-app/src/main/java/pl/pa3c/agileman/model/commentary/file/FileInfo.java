package pl.pa3c.agileman.model.commentary.file;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;

@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
@Data
public class FileInfo extends BaseLongEntity {

	public enum Type {
		COMMENT, CONTENT
	}

	protected String path;
	protected String fileName;
	@NotNull
	@Enumerated(EnumType.STRING)
	protected Type type;
}
