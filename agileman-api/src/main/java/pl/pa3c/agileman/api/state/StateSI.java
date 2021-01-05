package pl.pa3c.agileman.api.state;

import static pl.pa3c.agileman.api.state.StateSI.Constants.URL;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Api("State Management API")
@RequestMapping(URL)
public interface StateSI {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	final class Constants {
        public static final String URL = "/state";
    }
	
	@ApiOperation(value = "Modifies an entity.")
	@PutMapping(path = "/{id}/task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	StateSO updateName(@PathVariable(name = "id") Long id, @RequestBody StateSO stateSO);
	
	@ApiOperation(value = "Modifies entites.")
	@PutMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	List<StateSO> updateAll(@RequestBody List<StateSO> entitySO);
}
