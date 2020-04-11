package com.api.nach.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.api.nach.exceptions.ExceptionResponse;


public class CustomExceptionResponseHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AcctNotFoundException.class)
	public final ResponseEntity<Object> handleANFException(AcctNotFoundException ex, WebRequest request){
		
		String description = "Account Data Not Found";
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), description);
		
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}

}
