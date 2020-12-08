package pl.pa3c.agileman.repository.usertask;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.pa3c.agileman.model.task.Type;
import pl.pa3c.agileman.model.task.UserTask;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

	@Query("SELECT ut.type as type," + "ut.user.id as id," + "ut.user.name as name," + "ut.user.surname as surname"
			+ " FROM UserTask ut WHERE ut.task.id = ?1")
	List<ITaskUserInfo> getTaskBasicUserInfo(Long id);

	Optional<UserTask> findByTaskIdAndUserIdAndType(Long id, String id2, Type relationType);

	void deleteByTaskIdAndUserIdAndType(Long id, String login, Type valueOf);
}
