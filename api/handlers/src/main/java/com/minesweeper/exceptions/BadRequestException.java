package com.minesweeper.exceptions;

public class BadRequestException extends BaseException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message, 400, 5000);
	}

}
