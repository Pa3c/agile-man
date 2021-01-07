package pl.pa3c.agileman.api.user;

import lombok.Data;
import pl.pa3c.agileman.api.IdSO;

@Data
public class UserSpecializationSO extends IdSO<String> {
	private int skill;
}
