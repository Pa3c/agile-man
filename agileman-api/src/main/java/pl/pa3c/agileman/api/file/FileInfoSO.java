package pl.pa3c.agileman.api.file;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoSO {

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	public enum Type {
		TASK_COMMENT("TASK_COMMENT"), TASK("TASK"), DOC_COMMENT("DOC_COMMENT"), DOC("DOC");

		String value;
	}

	// to bedzie brane i zapisywane do bazy danych
	private Long resourceId;
	private Type type;
}
