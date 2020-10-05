package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.project.ProjectSO;
import pl.pa3c.agileman.model.project.Project;

@Service
public class ProjectService extends CommonService<Long, ProjectSO, Project>{


	@Autowired
	public ProjectService(JpaRepository<Project, Long> projectRepository) {
		super(projectRepository);
	}

}
