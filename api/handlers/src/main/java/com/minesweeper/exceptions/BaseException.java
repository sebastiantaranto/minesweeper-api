package com.minesweeper.exceptions;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int httpCode;
	private int errorCode;

	public BaseException(String message, int httpCode, int errorCode) {
		super(message);
		this.httpCode = httpCode;
		this.errorCode = errorCode;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
