package guru.springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormatError(Exception exception) {

	    log.error("Handling number format exception");
	    log.error(exception.getMessage());

	    ModelAndView modelAndView = new ModelAndView();

	    modelAndView.setViewName("400error");
	    modelAndView.addObject("exception", exception);

	    return modelAndView;
	}

}
