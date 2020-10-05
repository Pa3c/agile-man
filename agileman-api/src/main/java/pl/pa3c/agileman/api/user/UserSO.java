package pl.pa3c.agileman.api.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.BaseSO;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserSO extends BaseSO<String>{
	private String name;
	private String surname;
	
	@JsonAlias({"login","id"})
	@JsonProperty("login")
	@Override
	public String getId(){
		return id;
	}
	
	@JsonProperty("login")
	@Override
	public void setId(String id){
		this.id = id;
	}
}
