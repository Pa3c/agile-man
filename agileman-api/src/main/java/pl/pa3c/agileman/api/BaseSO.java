package pl.pa3c.agileman.api;

import java.time.LocalDateTime;


public class BaseSO<T> extends IdSO<T>{

	protected int version;
	protected LocalDateTime creationDate;
	protected LocalDateTime modificationDate;
	protected String createdBy;
	protected String modifiedBy;
}
