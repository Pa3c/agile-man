package pl.pa3c.agileman.api.file;

import static pl.pa3c.agileman.api.file.FileSI.Constants.URL;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@RequestMapping(URL)
public interface FileSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Constants {
		public static final String URL = "file";
	}
	@PostMapping
	public FileUploadSO save(@RequestParam("file") MultipartFile file,@RequestBody FileInfoSO info);
}
