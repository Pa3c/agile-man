package pl.pa3c.agileman.api.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class FilterSO {
	private Map<String,List<String>> propValueList;
	private Map<String,List<String>> propUserList;
	private Map<String,RangeProp> propNumberRange;
	private Map<String,LocalDateTime> propDate;
}
