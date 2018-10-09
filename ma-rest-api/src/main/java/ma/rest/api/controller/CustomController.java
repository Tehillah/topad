package ma.rest.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * Un service pour le test
 * @author TOBORI
 *
 */
@RestController
public class CustomController {

	@RequestMapping(value = "/custom", method = RequestMethod.POST)
	public String custom() {
		return "custom";

	}
}
