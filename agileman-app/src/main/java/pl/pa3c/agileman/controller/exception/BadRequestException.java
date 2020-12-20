package pl.pa3c.agileman.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends AgilemanException {

	private static final long serialVersionUID = 2820534370719982201L;

	public BadRequestException(String message) {
		super(message);
	}

}
