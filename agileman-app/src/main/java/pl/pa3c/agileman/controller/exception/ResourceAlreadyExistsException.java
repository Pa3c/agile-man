package pl.pa3c.agileman.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends AgilemanException {

    public ResourceAlreadyExistsException() {
        super("Resource already exists");
    }
}
