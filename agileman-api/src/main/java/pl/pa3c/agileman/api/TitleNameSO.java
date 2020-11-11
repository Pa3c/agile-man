package pl.pa3c.agileman.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TitleNameSO<T> extends IdSO<T> {
	String title;

	@JsonIgnore
	public void setName(String name) {
		this.title = name;
	}

	@JsonIgnore
	public String getName() {
		return title;
	}

}
