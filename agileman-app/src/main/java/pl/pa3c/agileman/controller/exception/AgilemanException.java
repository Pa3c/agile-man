package pl.pa3c.agileman.controller.exception;

import lombok.Getter;

@Getter
public class AgilemanException extends RuntimeException {

    public AgilemanException(String message) {
        super(message);
    }

	public AgilemanException(Throwable e, String message) {
		super(message, e);
	}
}
