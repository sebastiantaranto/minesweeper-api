package com.minesweeper.exceptions;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int httpCode;

	public BaseException(String message, int httpCode) {
		super(message);
		this.httpCode = httpCode;
	}

	public int getHttpCode() {
		return httpCode;
	}

}
