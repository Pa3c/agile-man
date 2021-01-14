package pl.pa3c.agileman.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import pl.pa3c.agileman.api.commentary.CommentarySO;
import pl.pa3c.agileman.api.commentary.TaskIdCommentary;
import pl.pa3c.agileman.controller.exception.BadRequestException;
import pl.pa3c.agileman.model.commentary.TaskCommentary;
import pl.pa3c.agileman.repository.TaskCommentaryRepository;

@Service
public class TaskCommentaryService extends CommonService<Long, CommentarySO, TaskCommentary> {

	@Autowired
	public TaskCommentaryService(JpaRepository<TaskCommentary, Long> repository) {
		super(repository);
	}

	public List<CommentarySO> getAllByResource(Long resourceId) {
		return ((TaskCommentaryRepository) repository).findAllByTaskId(resourceId).stream()
				.map(x -> {
					final CommentarySO so = mapper.map(x, CommentarySO.class);
					so.setContent(unescapeContent(x.getContent()));
					return so;
						}).collect(Collectors.toList());
	}

	@Override
	public CommentarySO create(CommentarySO entitySO) {
		final TaskIdCommentary com = new TaskIdCommentary(entitySO);
		com.setContent(escapeContent(com.getContent()));
		if (com.getResourceId() == null) {
			throw new BadRequestException("Cannot add not reference comment");
		}
		
		final CommentarySO so = super.create(com);
		so.setContent(unescapeContent(so.getContent()));
		return so;
	}

	@Override
	public List<CommentarySO> get() {
		final List<CommentarySO> so = super.get();
		so.forEach(x -> x.setContent(unescapeContent(x.getContent())));
		return so;

	}

	@Override
	public CommentarySO get(Long id) {
		final CommentarySO so = super.get(id);
		so.setContent(unescapeContent(so.getContent()));
		return so;
	}

	private String escapeContent(String content) {
		return HtmlUtils.htmlEscape(content);
	}

	private String unescapeContent(String content) {
		return HtmlUtils.htmlUnescape(content);
	}

}
