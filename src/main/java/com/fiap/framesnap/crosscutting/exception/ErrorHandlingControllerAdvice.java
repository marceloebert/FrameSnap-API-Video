package com.fiap.framesnap.crosscutting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse notFoundException(NotFoundException e) {
		
		ValidationErrorResponse error = new ValidationErrorResponse();
		error.add(new Violation(e.getMessage()));
		
		return error;
	}
    
}
