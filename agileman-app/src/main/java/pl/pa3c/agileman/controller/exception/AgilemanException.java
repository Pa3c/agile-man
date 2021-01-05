package pl.pa3c.agileman.controller.exception;

import lombok.Getter;

@Getter
public class AgilemanException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2560190580771694752L;

	public AgilemanException(String message) {
        super(message);
    }

	public AgilemanException(Throwable e, String message) {
		super(message, e);
	}
}
