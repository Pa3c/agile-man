package pl.pa3c.agileman.service;

import java.io.IOException;
import java.lang.module.ResolutionException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pa3c.agileman.api.file.FileUploadSO;
import pl.pa3c.agileman.controller.exception.FileStorageException;
import pl.pa3c.agileman.controller.exception.UnconsistentDataException;
import static pl.pa3c.agileman.service.FileService.Constants.*;

@Service
public class FileService {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	static final class Constants {
		private static final String BASIC_DIRECTORY = "/uczelnia/INZ/Aplikacja/Backend/agile-man/images";
		private static final String DOC_BASIC_DIRECTORY = BASIC_DIRECTORY + "/documentation";
		private static final String COMMENT_BASIC_DIRECTORY = BASIC_DIRECTORY + "/comment";
		private static final String TASK_COMMENT_DIRECTORY = BASIC_DIRECTORY + "/task";
		private static final String PROJECT_COMMENT_DIRECTORY = BASIC_DIRECTORY + "/project";
		private static final String DOC_COMMENT_DIRECTORY = BASIC_DIRECTORY + "/project";
	}

	private Path basicDirectory;

	public FileService() {
		basicDirectory = createDirectory(BASIC_DIRECTORY);
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

	public FileUploadSO save(MultipartFile file) {

		final String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			Path targetLocation = this.basicDirectory.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			final String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/static_file/")
					.path(fileName).toUriString();

			return new FileUploadSO(fileName, fileDownloadUri, file.getContentType(), targetLocation.toString(),
					file.getSize());
		} catch (IOException ex) {
			throw new UnconsistentDataException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.basicDirectory.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new ResolutionException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new ResolutionException("File not found " + fileName, ex);
		}
	}

}
