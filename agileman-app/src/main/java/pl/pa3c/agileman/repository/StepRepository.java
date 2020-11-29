package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.task.Step;

public interface StepRepository extends JpaRepository<Step, Long>{

}
