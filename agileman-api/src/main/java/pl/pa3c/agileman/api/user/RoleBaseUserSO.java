package pl.pa3c.agileman.api.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleBaseUserSO extends BaseUserSO {
	private String role;
}
