package pl.pa3c.agileman.api.team;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.user.RoleBaseUserSO;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TeamWithUsersSO extends TeamSO {
	private List<RoleBaseUserSO> users;
}
