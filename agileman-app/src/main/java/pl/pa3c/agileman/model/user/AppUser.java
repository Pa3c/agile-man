package pl.pa3c.agileman.model.user;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.BaseEntity;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class AppUser extends BaseEntity<String> {

	private String name;
	private String surname;
	private String email;
	private String password;
	private String phoneNumber;
	private String skype;
	private LocalDateTime birthday;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_role", 
				joinColumns = @JoinColumn(name = "user"), 
				inverseJoinColumns = @JoinColumn(name = "role"))
	private Set<Role> roles;
	
	public String getLogin(){
		return id;
	}
	
	public void setLogin(String login){
		this.id = login;
	}
}
