package pl.pa3c.agileman.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface BaseCrudInterface<T, ID> {
	@ApiOperation(value = "Finds all entities.", responseContainer = "List")
	@GetMapping
	List<T> get();

	@ApiOperation(value = "Returns a specified entity.")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Entity not found"),
			@ApiResponse(code = 200, message = "Entity found") })
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	T get(@PathVariable(name = "id") ID id);

	@ApiOperation(value = "Creates an entity.")
	@ApiResponses(value = { @ApiResponse(code = 409, message = "Resource already exists"),
			@ApiResponse(code = 201, message = "Entity created") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	T create(@RequestBody T entitySO);

	@ApiOperation(value = "Modifies an entity.")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	T update(@PathVariable(name = "id") ID id, @RequestBody T entitySO);
	
	@ApiOperation(value = "Delete a League.")
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	void delete(@PathVariable(name = "id") ID id);

}
