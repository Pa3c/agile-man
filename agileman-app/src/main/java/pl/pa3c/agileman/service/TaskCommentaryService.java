package pl.pa3c.agileman.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.pa3c.agileman.api.commentary.CommentarySO;
import pl.pa3c.agileman.api.commentary.TaskIdCommentary;
import pl.pa3c.agileman.controller.exception.BadRequestException;
import pl.pa3c.agileman.model.commentary.TaskCommentary;
import pl.pa3c.agileman.repository.TaskCommentaryRepository;

@Service
public class TaskCommentaryService extends CommonService<Long, CommentarySO, TaskCommentary>{


	@Autowired
	public TaskCommentaryService(JpaRepository<TaskCommentary, Long> repository) {
		super(repository);
	}

	public List<CommentarySO> getAllByResource(Long resourceId) {
		return ((TaskCommentaryRepository)repository).findAllByTaskId(resourceId)
				.stream().map(x->mapper.map(x,CommentarySO.class)).collect(Collectors.toList());
	}
	
	@Override
	public CommentarySO create(CommentarySO entitySO) {
		TaskIdCommentary com = (TaskIdCommentary)entitySO;
		
		if(com.getResourceId()==null) {
			throw new BadRequestException("Cannot add not reference comment");
		}
		return super.create(com);
	}

}
