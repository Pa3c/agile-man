package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.label.Label;

public interface LabelRepository extends JpaRepository<Label, String>{

}
