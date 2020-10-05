package pl.pa3c.agileman.controller.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceIsInUseException extends AgilemanException {

	public ResourceIsInUseException(DataIntegrityViolationException e) {
		super(e, "Resource is already in use");
	}
}
