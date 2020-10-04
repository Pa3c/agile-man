package pl.pa3c.agileman.model.commentary;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.project.Project;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class ProjectCommentary extends BaseCommentary{

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
}