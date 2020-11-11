package pl.pa3c.agileman.api.user;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.IdSO;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserTeamProjectSO extends IdSO<Long> {
	protected Set<String> roles;
	protected String title;
}
