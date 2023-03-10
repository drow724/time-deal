package com.timedeal.common.advice;

import java.util.NoSuchElementException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.timedeal.common.response.ErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(IllegalAccessException.class)
	public ResponseEntity<ErrorResponse> IllegalAccessHandle(IllegalAccessException e) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> noSuchElementExceptionHandle(NoSuchElementException e) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> IllegalArgumentHandle(IllegalArgumentException e) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ErrorResponse> DuplicateKeyHandle(DuplicateKeyException e) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
