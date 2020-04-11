package com.api.nach.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AcctNotFoundException extends RuntimeException{

	public AcctNotFoundException(String message) {
		super(message);
	}

	

}
