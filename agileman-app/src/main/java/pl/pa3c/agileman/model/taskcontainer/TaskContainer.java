package pl.pa3c.agileman.model.taskcontainer;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.model.base.BaseLongEntity;
import pl.pa3c.agileman.model.project.TeamInProject;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskContainer extends BaseLongEntity{
	private String title;
	private Boolean closed = false;
	
	//THIS IS FOR XP PURPOSES
	private Boolean abandoned = false;
	
	//THIS IS ONLY FOR SCRUM PURPOSES
	private LocalDateTime openDate;
	private LocalDateTime closeDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;

	@ManyToOne
	@JoinColumn(name = "overcontainer_id")
	private TaskContainer overcontainer;
	
	@ManyToOne
	@JoinColumn(name="team_in_project_id")
	private TeamInProject teamInProject;
	
	public void setType(String type){
		this.type = Type.valueOf(type);
	}
	
	public String getStringType() {
		return type.toString();
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
}
