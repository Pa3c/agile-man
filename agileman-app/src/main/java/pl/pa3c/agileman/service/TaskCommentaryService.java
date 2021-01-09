package pl.pa3c.agileman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.commentary.CommentarySO;
import pl.pa3c.agileman.model.commentary.TaskCommentary;

@Service
public class TaskCommentaryService extends CommonService<Long, CommentarySO, TaskCommentary>{


	@Autowired
	public TaskCommentaryService(JpaRepository<TaskCommentary, Long> repository) {
		super(repository);
	}

}
