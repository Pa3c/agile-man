package pl.pa3c.agileman.api.task;

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
public class TaskUserSO extends IdSO<String> {
	private String name;
	private String surname;
	private String type;

	public TaskUserSO(final String id, final String name, final String surname, final String type) {
		this(name, surname, type);
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
