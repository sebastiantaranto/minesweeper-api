package com.minesweeper.exceptions;

public class PreconditionFailedException extends BaseException {

	private static final long serialVersionUID = 1L;

	public PreconditionFailedException(String message) {
		super(message, 412, 412);
	}

}
