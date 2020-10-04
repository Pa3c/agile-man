package pl.pa3c.agileman.model.user;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.BaseEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity<String> {

	private String name;
	private String surname;

	@Embedded
	private UserInfo userInfo;
	
	public String getLogin(){
		return id;
	}
	
	public void setLogin(String login){
		this.id = login;
	}
}
