package pl.pa3c.agileman.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pa3c.agileman.model.commentary.file.TaskFileInfo;

public interface TaskFileRepository extends JpaRepository<TaskFileInfo, Long>{

}
