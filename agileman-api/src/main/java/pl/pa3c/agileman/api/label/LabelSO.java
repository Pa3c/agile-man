package pl.pa3c.agileman.api.label;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.IdSO;

@Data
@EqualsAndHashCode(callSuper = false)
public class LabelSO extends IdSO<String>{
	
	private String type;
	
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
