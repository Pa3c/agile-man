package pl.pa3c.agileman.events;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.task.Type;
import pl.pa3c.agileman.model.task.UserTask;
import pl.pa3c.agileman.repository.usertask.UserTaskRepository;
import pl.pa3c.agileman.service.TaskNotificationService;

@Component
public class TaskEventHandler {

	@Autowired
	private TaskNotificationService taskNotificationService;

	@Autowired
	private UserTaskRepository userTaskRepository;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	@Async
	public void afterTransaction(TaskUpdateEvent event) throws IllegalAccessException, MessagingException {

		final List<UserTask> userTasks = userTaskRepository.findAllByTaskIdAndTypeIn(event.getNewState().getId(),
				Arrays.asList(Type.OBSERVER));

		if (userTasks.isEmpty()) {
			return;
		}

		final Map<String, List<String>> taskDifferences = findTaskDifferences(event.getOldState(), event.getNewState());

		if (taskDifferences.keySet().isEmpty()) {
			return;
		}

		taskNotificationService.buildAndSendMail(userTasks, event.getOldState().getTitle(), taskDifferences);
	}

	private Map<String, List<String>> findTaskDifferences(Task oldTask, Task newTask) throws IllegalAccessException {

		final Map<String, List<String>> notMatchProperties = new HashMap<>();

		List<Field> fields = this.getFields(oldTask);

		for (Field f : fields) {
			f.setAccessible(true);
			final Object oldValue = f.get(oldTask);
			final Object newValue = f.get(newTask);

			if (oldValue == null) {
				if (newValue != null) {
					notMatchProperties.put(f.getName(), Arrays.asList("", newValue.toString()));
				}
				continue;
			}

			if (!oldValue.equals(newValue)) {
				notMatchProperties.put(f.getName(), Arrays.asList(oldValue.toString(), newValue.toString()));
			}
			f.setAccessible(false);
		}
		return notMatchProperties;
	}

	private <T> List<Field> getFields(T t) {
		final List<Field> fields = new ArrayList<>();
		Class<?> clazz = t.getClass();
		while (clazz != Object.class) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fields;
	}
}
