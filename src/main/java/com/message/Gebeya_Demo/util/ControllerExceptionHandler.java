package com.message.Gebeya_Demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.micrometer.core.instrument.config.validate.ValidationException;

//@ControllerAdvice 
public class ControllerExceptionHandler {

	@ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	public ErrorMessage exceptionHandler(RuntimeException e) {
		return new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.name());
	}
	
}
