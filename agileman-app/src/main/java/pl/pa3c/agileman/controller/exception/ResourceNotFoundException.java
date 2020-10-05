package pl.pa3c.agileman.controller.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends AgilemanException {

	public ResourceNotFoundException(EmptyResultDataAccessException e, String id) {
        super(e, "Resource with id=" + id + " not found : " + (e != null ? e.getMessage() : ""));
    }

}
