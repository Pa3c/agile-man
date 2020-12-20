package pl.pa3c.agileman.api.user;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MultiRoleBaseUserSO extends BaseUserSO {
	private List<String> roles;
}
