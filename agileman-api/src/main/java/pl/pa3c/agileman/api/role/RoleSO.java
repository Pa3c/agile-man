package pl.pa3c.agileman.api.role;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.IdSO;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RoleSO extends IdSO<String>{
	
	
	public RoleSO(String id) {
		this.id = id;
	}
	
	@JsonAlias({"name","id"})
	@JsonProperty("name")
	@Override
	public String getId() {
		return super.getId();
	}
	
	@JsonAlias({"name","id"})
	@JsonProperty("name")
	@Override
	public void setId(String id) {
		super.setId(id);
	}
	
}
