package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.label.Label;
import pl.pa3c.agileman.model.label.Type;

public interface LabelRepository extends JpaRepository<Label, String>{
	List<Label> findByTypeAndIdContainingIgnoreCase(Type type, String id);
}
