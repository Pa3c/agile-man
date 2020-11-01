package pl.pa3c.agileman.api;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaseSO<T> extends IdSO<T>{

	protected int version;
	protected LocalDateTime creationDate;
	protected LocalDateTime modificationDate;
	protected String createdBy;
	protected String modifiedBy;
	
	public BaseSO(BaseSO<T> baseSO){
		super(baseSO);
		this.createdBy = baseSO.createdBy;
		this.creationDate = baseSO.creationDate;
		this.modificationDate = baseSO.modificationDate;
		this.modifiedBy = baseSO.modifiedBy;
		this.version = baseSO.version;
	}
}
