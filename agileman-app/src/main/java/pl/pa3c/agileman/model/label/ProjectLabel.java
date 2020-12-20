package pl.pa3c.agileman.model.label;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.model.base.StringIdEntity;
import pl.pa3c.agileman.model.project.Project;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ProjectLabelId.class)
public class ProjectLabel extends StringIdEntity {
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Enumerated(EnumType.STRING)
	private Type type;

	public ProjectLabel(String id, Type type, Project project) {
		this(project, type);
		this.id = id;
	}

}
