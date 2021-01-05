package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.task.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findByTaskContainerId(Long id);
	List<Task> findByTaskContainerIdAndState(Long id, String state);
	void deleteAllByTaskContainerIdAndState(Long id, String name);
	List<Task> findByTaskContainerIdAndClosedIsNull(Long id);

}
