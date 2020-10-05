package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.task.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
