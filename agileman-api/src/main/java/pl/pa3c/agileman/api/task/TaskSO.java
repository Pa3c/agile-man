package pl.pa3c.agileman.api.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.BaseSO;
import pl.pa3c.agileman.api.common.DtFormat;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskSO extends BaseSO<Long>{
	private String title;
	private String description;
	private Integer storyPoints;
	@JsonFormat(pattern =  DtFormat.COMMON)
	private LocalDateTime deadline;
	@JsonFormat(pattern =  DtFormat.COMMON)
	private LocalDateTime closed;
	@JsonFormat(pattern =  DtFormat.COMMON)
	private LocalDateTime reopened;
	private String type;
	private Integer likes;
	private Integer majority;
	private Integer complexity;
	private String state;
	private String solution;
	private String labels;
	private String technologies;
	private Long taskContainerId;
	private Long projectId;
	private List<StepSO> steps = new ArrayList<>();
}
