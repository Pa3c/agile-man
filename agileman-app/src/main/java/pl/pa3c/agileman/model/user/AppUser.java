package pl.pa3c.agileman.model.user;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseStringEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class AppUser extends BaseStringEntity {

	private String name;
	private String surname;
	private String email;
	private String password;
	private String phoneNumber;
	private String skype;
	private String description;
	private byte[] photo;
	private LocalDateTime birthday;

	public String getLogin() {
		return id;
	}

	public void setLogin(String login) {
		this.id = login;
	}

}
