package pl.pa3c.agileman.model.label;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProjectLabelId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3707983312885211031L;
	private String id;
	private Long projectId;
}
