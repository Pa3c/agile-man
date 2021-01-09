package pl.pa3c.agileman.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import pl.pa3c.agileman.api.commentary.CommentarySO;
import pl.pa3c.agileman.api.commentary.task.TaskCommentarySI;
import pl.pa3c.agileman.model.commentary.TaskCommentary;
import pl.pa3c.agileman.service.CommonService;

@RestController
@CrossOrigin
public class TaskCommentaryController extends CommonController<Long, CommentarySO, TaskCommentary> implements TaskCommentarySI{

	public TaskCommentaryController(CommonService<Long, CommentarySO, TaskCommentary> commonService) {
		super(commonService);
	}

}
