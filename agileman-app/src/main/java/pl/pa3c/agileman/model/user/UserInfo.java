package pl.pa3c.agileman.model.user;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UserInfo {

	private String email;
	private String phoneNumber;
	private String skype;
	private LocalDateTime birthday;
}
