package pl.pa3c.agileman.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.common.DtFormat;

@NoArgsConstructor
@Data
public class BaseSO<T> extends IdSO<T> {

	protected int version;
	@JsonFormat(pattern = DtFormat.COMMON)
	protected LocalDateTime creationDate;
	@JsonFormat(pattern = DtFormat.COMMON)
	protected LocalDateTime modificationDate;
	protected String createdBy;
	protected String modifiedBy;

	public BaseSO(BaseSO<T> baseSO) {
		super(baseSO);
		this.createdBy = baseSO.createdBy;
		this.creationDate = baseSO.creationDate;
		this.modificationDate = baseSO.modificationDate;
		this.modifiedBy = baseSO.modifiedBy;
		this.version = baseSO.version;
	}
}
