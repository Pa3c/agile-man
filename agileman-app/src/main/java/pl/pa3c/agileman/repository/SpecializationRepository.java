package pl.pa3c.agileman.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.user.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, String>{

	Optional<Specialization> findByIdIgnoreCase(String name);

	List<Specialization> findByIdContainingIgnoreCase(String name);
}
