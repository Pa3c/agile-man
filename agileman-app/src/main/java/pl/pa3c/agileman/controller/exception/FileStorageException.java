package pl.pa3c.agileman.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileStorageException extends AgilemanException {

	private static final long serialVersionUID = 9032229625293505640L;

	public FileStorageException(String msg) {
		super(msg);
	}

	public FileStorageException(String msg, Throwable ex) {
		super(ex, msg);
	}

}
