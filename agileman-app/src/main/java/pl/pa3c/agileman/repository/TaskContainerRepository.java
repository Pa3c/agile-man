package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.taskcontainer.TaskContainer;

public interface TaskContainerRepository extends JpaRepository<TaskContainer, Long>{

}