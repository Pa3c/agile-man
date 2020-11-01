package pl.pa3c.agileman.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdSO<T> {
	public IdSO(IdSO<T> idSO) {
		this.id = idSO.id;
	}

	protected T id;
}
