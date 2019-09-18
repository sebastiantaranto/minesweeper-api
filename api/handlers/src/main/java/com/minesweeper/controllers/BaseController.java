package com.minesweeper.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.collect.Iterables;
import com.minesweeper.exceptions.BaseException;
import com.minesweeper.response.ErrorResponse;

public class BaseController extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult bindingResult = ex.getBindingResult();
		if (bindingResult != null && bindingResult.getAllErrors() != null && !bindingResult.getAllErrors().isEmpty()) {
			ObjectError error = Iterables.getFirst(bindingResult.getAllErrors(), null);
			ErrorResponse errorResponse = new ErrorResponse();
			if (error instanceof FieldError) {
				FieldError field = (FieldError) error;
				errorResponse.setMessage(field.getDefaultMessage());
				errorResponse.setField(field.getField());
			}

			return new ResponseEntity<Object>(errorResponse, status);
		}
		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

	protected ResponseEntity<Object> processException(Exception e) {
		logger.error("There was an error processing the request", e);
		ErrorResponse response = new ErrorResponse();
		response.setMessage(e.getMessage());
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (e instanceof BaseException) {
			BaseException ex = (BaseException) e;
			status = HttpStatus.valueOf(ex.getHttpCode());
		}
		return new ResponseEntity<Object>(response, status);
	}
}
