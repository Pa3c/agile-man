package pl.pa3c.agileman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.taskcontainer.State;

public interface StateRepository extends JpaRepository<State, Long>{
	List<State> findByTaskContainerId(Long id);

}
