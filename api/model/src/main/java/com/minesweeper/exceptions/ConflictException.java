package com.minesweeper.exceptions;

public class ConflictException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ConflictException(String message) {
		super(message, 409);
	}

}
