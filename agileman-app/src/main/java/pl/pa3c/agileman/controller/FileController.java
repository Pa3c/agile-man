package pl.pa3c.agileman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.pa3c.agileman.api.file.FileInfoSO;
import pl.pa3c.agileman.api.file.FileSI;
import pl.pa3c.agileman.api.file.FileUploadSO;
import pl.pa3c.agileman.service.FileService;

@RestController
@CrossOrigin
public class FileController implements FileSI {

	@Autowired
	private FileService fileService;

	@Override
	public FileUploadSO save(MultipartFile file, String resourceId, String type) {
		return fileService.save(file,
				new FileInfoSO(Long.valueOf(resourceId), FileInfoSO.Type.valueOf(type.toUpperCase())));
	}
}
