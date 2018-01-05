package com.test.security.configurations.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.test.security.exceptions.APIException;

@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<APIException> handleAuthenticationException(AuthenticationException exception) {
		return new ResponseEntity<APIException>(new APIException("Authentication Failed", HttpStatus.UNAUTHORIZED),
				HttpStatus.UNAUTHORIZED);
	}
}

