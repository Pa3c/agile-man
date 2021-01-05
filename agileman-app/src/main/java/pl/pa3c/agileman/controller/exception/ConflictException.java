package pl.pa3c.agileman.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends AgilemanException {

	private static final long serialVersionUID = 1923618986248352220L;

	public ConflictException(String msg) {
        super(msg);
    }
}
