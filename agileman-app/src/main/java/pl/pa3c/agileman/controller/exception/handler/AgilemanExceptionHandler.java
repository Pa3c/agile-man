package pl.pa3c.agileman.controller.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.pa3c.agileman.controller.exception.AgilemanException;

import java.time.LocalDateTime;


@ControllerAdvice
public class AgilemanExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = AgilemanException.class)
	public ResponseEntity<ErrorResponse> handleKalkException(AgilemanException ex, WebRequest request) {
		ErrorResponse errorMessage = new ErrorResponse();
		errorMessage.setTimestamp(LocalDateTime.now());
		errorMessage.setError(ex.getMessage());
		final ResponseStatus[] annotationsByType = ex.getClass().getAnnotationsByType(ResponseStatus.class);
		return new ResponseEntity<>(errorMessage, annotationsByType[0].value());
	}
}
