package pl.pa3c.agileman.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class NotSupportedOperationException extends AgilemanException {

	private static final long serialVersionUID = 9032229625293505640L;

	public NotSupportedOperationException(String msg) {
		super(msg);
	}

	public NotSupportedOperationException(String msg, Throwable ex) {
		super(ex, msg);
	}

}
