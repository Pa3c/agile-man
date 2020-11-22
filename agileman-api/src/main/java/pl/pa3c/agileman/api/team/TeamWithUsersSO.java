package pl.pa3c.agileman.api.team;



import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.user.UserSO;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TeamWithUsersSO extends TeamSO {
	private Set<UserSO> users;
}
