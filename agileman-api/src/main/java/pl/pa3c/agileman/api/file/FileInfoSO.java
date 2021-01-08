package pl.pa3c.agileman.api.file;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FileInfoSO{

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	enum Type {
		TASK_COMMENT("TASK_COMMENT"), DOC_COMMENT("DOC_COMMENT"),DOC("DOC");
		String value;
	}
	
	//to bedzie brane i zapisywane do bazy danych 
	private Long resourceId;
	
}
