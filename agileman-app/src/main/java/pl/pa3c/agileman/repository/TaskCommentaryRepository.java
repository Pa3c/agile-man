package pl.pa3c.agileman.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.commentary.TaskCommentary;

public interface TaskCommentaryRepository extends JpaRepository<TaskCommentary, Long> {

	Collection<TaskCommentary> findAllByTaskId(Long resourceId);

}
