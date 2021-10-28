package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationFailureException extends RuntimeException{

	public ValidationFailureException(String message) {
		super(message);
	}
}
