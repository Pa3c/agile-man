package pl.pa3c.agileman.api.label;

import static pl.pa3c.agileman.api.label.LabelSI.Constants.URL;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Label  Management API")
@RequestMapping(URL)
public interface LabelSI {

	final class Constants {
        public static final String URL = "/label";
        private Constants() {
            super();
        }
    }
	
	@ApiOperation(value = "Get filtered labels")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Labels returned successfully"),
			})
	@GetMapping(path = "/type/{type}/filtered", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	List<LabelSO> getFiltered(@PathVariable("type")String type,@RequestParam(name="value")String value);
}
