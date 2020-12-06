package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.label.ProjectLabel;

public interface ProjectLabelRepository extends JpaRepository<ProjectLabel, String> {

}
