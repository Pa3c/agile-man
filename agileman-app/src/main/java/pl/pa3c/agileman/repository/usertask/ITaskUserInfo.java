package pl.pa3c.agileman.repository.usertask;

import pl.pa3c.agileman.model.task.Type;

public interface ITaskUserInfo {
	String getId();

	String getName();

	String getSurname();

	Type getType();
}
