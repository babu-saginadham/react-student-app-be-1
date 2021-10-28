package com.exception;

import java.util.Date;

import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
	
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> resourceNotFoundException(ValidationException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ValidationFailureException.class)
	public ResponseEntity<?> validationFailureException(ValidationFailureException ex, WebRequest request) {
		logger.info("I am inside validationFailureException");
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		logger.info("I am inside globleExcpetionHandler");
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		logger.info("I am inside handleMethodArgumentNotValid");
		/*
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
				ex.getBindingResult().toString());
		*/
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
				"Request Payload Failures Happened");
		
		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	} 

}
