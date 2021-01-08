package pl.pa3c.agileman.api.task;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.file.FileUploadSO;

public interface TaskCommentSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Constants {
		public static final String URL = "taskcomment";
	}
	@PostMapping
	public FileUploadSO addFile(@RequestParam("file") MultipartFile file);
}
