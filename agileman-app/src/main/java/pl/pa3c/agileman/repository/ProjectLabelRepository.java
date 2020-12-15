package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.api.label.LabelSO;
import pl.pa3c.agileman.model.label.ProjectLabel;

public interface ProjectLabelRepository extends JpaRepository<ProjectLabel, String> {
	List<ProjectLabel> findByProjectId(Long projectId);

	List<LabelSO> findByProjectIdAndTypeAndIdLike(Long projectId, String type, String id);
}
