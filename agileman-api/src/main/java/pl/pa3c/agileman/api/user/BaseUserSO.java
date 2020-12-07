package pl.pa3c.agileman.api.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.IdSO;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserSO extends IdSO<String> {
	private String name;
	private String surname;

	public BaseUserSO(final String id, final String name, final String surname) {
		this(name, surname);
		this.id = id;
	}

	@JsonAlias({ "login", "id" })
	@JsonProperty("login")
	@Override
	public String getId() {
		return id;
	}

	@JsonProperty("login")
	@Override
	public void setId(String id) {
		this.id = id;
	}
}
