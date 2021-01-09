package pl.pa3c.agileman.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FailedSaveFile {
	private String fileLocation;
	private Long resourceId;
}
