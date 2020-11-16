package pl.pa3c.agileman.api.state;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pa3c.agileman.api.IdSO;


@Data
@EqualsAndHashCode(callSuper = false)
public class StateSO extends IdSO<Long>{
	private String name;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String oldName;
	private Integer order;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long taskContainerId;
}
