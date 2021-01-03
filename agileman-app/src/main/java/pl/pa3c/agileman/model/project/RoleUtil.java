package pl.pa3c.agileman.model.project;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;

public class RoleUtil {

	private static final Map<String, Class<? extends Enum<?>>> MAP = new HashMap<>();

	static {
		MAP.put(SCRUM.class.getSimpleName(), SCRUM.class);
		MAP.put(XP.class.getSimpleName(), XP.class);
		MAP.put(KANBAN.class.getSimpleName(), KANBAN.class);
	}

	public enum SCRUM {
		TESTER, SCRUM_MASTER, DEV, PRODUCT_OWNER, LEADER, CUSTOMER,PROJECT_SUPER_ADMIN,PROJECT_BASIC
	}

	public enum XP {
		TESTER, DEV, PRODUCT_OWNER, CUSTOMER,PROJECT_SUPER_ADMIN,PROJECT_BASIC
	}

	public enum KANBAN {
		TESTER, DEV, PRODUCT_OWNER, LEADER, CUSTOMER,PROJECT_SUPER_ADMIN,PROJECT_BASIC
	}

	public static TeamProjectRole toEnum(String value) {
		value = value.toUpperCase().replace(" ", "_");
		return TeamProjectRole.valueOf(value);
	}

	public static String toString(TeamProjectRole role) {
		return role.name().toUpperCase().replace("_", " ");
	}

	public static boolean isScrumRole(String role) {
		return isScrumRole(toEnum(role));
	}

	public static boolean isXpRole(String role) {
		return isXpRole(toEnum(role));
	}

	public static boolean isKanbanRole(String role) {
		return isKanbanRole(toEnum(role));
	}

	public static boolean isScrumRole(TeamProjectRole role) {
		return isValueOf(SCRUM.class, role.name());
	}

	public static boolean isXpRole(TeamProjectRole role) {
		return isValueOf(XP.class, role.name());
	}

	public static boolean isKanbanRole(TeamProjectRole role) {
		return isValueOf(KANBAN.class, role.name());
	}

	public static List<String> getRolesByType(String type) {
		if (!MAP.containsKey(type.toUpperCase())) {
			throw new ResourceNotFoundException("Project type " + type + " does not exist.");
		}
		return Arrays.asList(MAP.get(type.toUpperCase()).getEnumConstants()).stream().map(Enum::name)
				.collect(Collectors.toList());
	}

	private static <T extends Enum<T>> boolean isValueOf(Class<T> clazz, String role) {
		try {
			Enum.valueOf(clazz, role);
			TeamProjectRole.valueOf(role);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
