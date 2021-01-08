package pl.pa3c.agileman.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnconsistentDataException extends AgilemanException {

	private static final long serialVersionUID = 9032229625293505640L;

	public UnconsistentDataException(String msg) {
		super(msg);
	}
	
	public UnconsistentDataException(String msg,Throwable ex) {
		super(ex,msg);
	}

}
