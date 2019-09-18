package com.minesweeper.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.minesweeper.exceptions.BaseException;
import com.minesweeper.response.ErrorResponse;

public class BaseController extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected ResponseEntity<Object> processException(Exception e) {
		logger.error("There was an error processing the request", e);
		ErrorResponse response = new ErrorResponse();
		response.setMessage(e.getMessage());
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (e instanceof BaseException) {
			BaseException ex = (BaseException) e;
			response.setCode(ex.getErrorCode());
			status = HttpStatus.valueOf(ex.getHttpCode());
		} 
		return new ResponseEntity<Object>(response, status);
	}
}
