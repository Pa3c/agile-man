package pl.pa3c.agileman.api.user;

import java.time.LocalDateTime;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.BaseSO;
import pl.pa3c.agileman.api.common.DtFormat;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserSO extends BaseSO<String>{
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private String skype;
	private String description;
	//private String photo;
	@JsonFormat(pattern = DtFormat.COMMON)
	private LocalDateTime birthday;
	
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
//	
//	
//	public void setPhoto(byte[] photo){
//		this.photo = photo==null ? null : new String(Base64.getEncoder().encode(photo)); 
//	}
//	
//	@JsonAlias({"photo","photo"})
//	@JsonProperty("photo")
//	public String getThePhoto(){
//		return photo==null ? null : photo; 
//	}
//	
//	@JsonIgnore
//	@JsonAlias({"invalid_property","invalid_property"})
//	@JsonProperty("invalid_property")
//	public byte[] getPhoto() {
//		return photo == null ? null : Base64.getDecoder().decode(photo);
//	}
//	
//	public void setPhoto(String photo) {
//		this.photo = photo;
//	}
}
