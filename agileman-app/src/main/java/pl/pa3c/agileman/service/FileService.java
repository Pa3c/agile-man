package pl.pa3c.agileman.service;

import java.io.IOException;
import java.lang.module.ResolutionException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.file.FileInfoSO;
import pl.pa3c.agileman.api.file.FileUploadSO;
import pl.pa3c.agileman.controller.exception.FileStorageException;
import pl.pa3c.agileman.controller.exception.ResourceNotFoundException;
import pl.pa3c.agileman.controller.exception.UnconsistentDataException;
import pl.pa3c.agileman.events.FailedSaveFile;
import pl.pa3c.agileman.model.commentary.file.DocumentationFileInfo;
import pl.pa3c.agileman.model.commentary.file.FileInfo;
import pl.pa3c.agileman.model.commentary.file.TaskFileInfo;
import pl.pa3c.agileman.model.documentation.Documentation;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.repository.DocumentationFileRepository;
import pl.pa3c.agileman.repository.DocumentationRepository;
import pl.pa3c.agileman.repository.TaskFileRepository;
import pl.pa3c.agileman.repository.TaskRepository;

@Service
public class FileService {

	@Autowired
	private TaskFileRepository taskFileRepository;
	@Autowired
	private DocumentationFileRepository docFileRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private DocumentationRepository docRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	static final class Constants {
		private static final String BASIC_DIRECTORY = "/uczelnia/INZ/Aplikacja/Backend/agile-man/images";
	}

	private Path createDirectory(String directory) {
		final Path path = Paths.get(directory).toAbsolutePath().normalize();
		try {
			return Files.createDirectories(path);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}

	}

	@Transactional
	public FileUploadSO save(MultipartFile file, FileInfoSO info) {
		final String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			final String uniqueFileName = createUniqueName(fileName);
			final String pathDirectory = Constants.BASIC_DIRECTORY + "/" + info.getType() + "/" + info.getResourceId()
					+ "/";
			final String fileUrl = pathDirectory + uniqueFileName;

			final Path tempDir = createDirectory(pathDirectory);
			final Path targetLocation = tempDir.resolve(fileUrl);
			
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			final String uriLocation = pathDirectory.substring(Constants.BASIC_DIRECTORY.length() + 1,
					pathDirectory.length());
			
			final String changedUriLocation = uriLocation.replaceAll("/", "&");
			
			
			final String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/static_file/")
					.path(changedUriLocation+uniqueFileName).toUriString();

			createFileInfo(targetLocation.toString(), info);

			return new FileUploadSO(fileName, fileDownloadUri, file.getContentType(), targetLocation.toString(),
					file.getSize());
		} catch (IOException ex) {
			throw new UnconsistentDataException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	private String createUniqueName(String fileName) {
		final String currentTIme = String.valueOf(System.currentTimeMillis());
		return currentTIme + fileName;
	}

	@Transactional
	private void createFileInfo(String targetLocation, FileInfoSO info) {
		publisher.publishEvent(new FailedSaveFile(targetLocation, info.getResourceId()));
		switch (info.getType()) {
		case TASK_COMMENT:
			final Task t = findById(info.getResourceId(), this.taskRepository);
			createTaskFileInfo(t, FileInfo.Type.COMMENT, targetLocation);
			break;
		case TASK:
			final Task t2 = findById(info.getResourceId(), this.taskRepository);
			createTaskFileInfo(t2, FileInfo.Type.CONTENT, targetLocation);
			break;
		case DOC_COMMENT:
			final Documentation d = findById(info.getResourceId(), this.docRepository);
			createDocFileInfo(d, FileInfo.Type.COMMENT, targetLocation);
			break;
		case DOC:
			final Documentation d2 = findById(info.getResourceId(), this.docRepository);
			createDocFileInfo(d2, FileInfo.Type.CONTENT, targetLocation);
			break;
		}
	}

	@Transactional
	private DocumentationFileInfo createDocFileInfo(Documentation doc, FileInfo.Type content, String ultimateFileName) {
		final DocumentationFileInfo entity = new DocumentationFileInfo();
		entity.setDocumentation(doc);
		entity.setType(FileInfo.Type.CONTENT);
		entity.setFileName(ultimateFileName);
		return docFileRepository.save(entity);
	}

	@Transactional
	private TaskFileInfo createTaskFileInfo(Task t, FileInfo.Type content, String ultimateFileName) {
		final TaskFileInfo entity = new TaskFileInfo();
		entity.setTask(t);
		entity.setType(FileInfo.Type.CONTENT);
		entity.setFileName(ultimateFileName);
		return taskFileRepository.save(entity);
	}

	private <T> T findById(Long resourceId, JpaRepository<T, Long> repository) {
		return repository.findById(resourceId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource of id" + resourceId + " not found"));
	}

	public Resource loadFileAsResource(String targetUri) {
		try {
			
			final String changedUriLocation = targetUri.replaceAll("&", "/");

			
			final Path tempDir = Paths.get(Constants.BASIC_DIRECTORY+"/"+changedUriLocation);
			Resource resource = new UrlResource(tempDir.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new ResolutionException("File not found " + targetUri);
			}
		} catch (MalformedURLException ex) {
			throw new ResolutionException("File not found " + targetUri, ex);
		}
	}
}
