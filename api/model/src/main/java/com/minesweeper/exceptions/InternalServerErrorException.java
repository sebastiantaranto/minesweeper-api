package com.minesweeper.exceptions;

public class InternalServerErrorException extends BaseException {

	private static final long serialVersionUID = 1L;

	public InternalServerErrorException(String message) {
		super(message, 500);
	}

}
