package pl.pa3c.agileman.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.label.ProjectLabel;
import pl.pa3c.agileman.model.label.Type;

public interface ProjectLabelRepository extends JpaRepository<ProjectLabel, String> {
	List<ProjectLabel> findByProjectId(Long projectId);
	List<ProjectLabel> findByProjectIdAndTypeAndIdContainingIgnoreCase(Long projectId, Type type, String id);
	@Transactional
	void deleteAllByIdAndProjectId(String labelId, Long projectId);
}
