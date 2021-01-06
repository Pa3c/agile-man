package pl.pa3c.agileman.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.taskcontainer.TaskContainer;
import pl.pa3c.agileman.model.taskcontainer.Type;

public interface TaskContainerRepository extends JpaRepository<TaskContainer, Long>{

	List<TaskContainer> findAllByTeamInProjectId(Long id);
	
	@Transactional
	void deleteAllByTeamInProjectId(Long id);
	List<TaskContainer> findByCloseDateAfterAndClosedIsFalse(LocalDateTime now);

	TaskContainer findFirstByTeamInProjectIdAndType(Long tipId, Type backlog);

}
