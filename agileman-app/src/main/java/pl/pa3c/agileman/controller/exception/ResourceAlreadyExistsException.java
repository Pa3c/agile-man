package pl.pa3c.agileman.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends AgilemanException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4220765434745339357L;

	public ResourceAlreadyExistsException() {
		super("Resource already exists");
	}
}
