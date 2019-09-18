package com.minesweeper.exceptions;

public class EntityAlreadyExist extends BaseException {

	private static final long serialVersionUID = 1L;

	public EntityAlreadyExist(String message) {
		super(message, 400);
	}

}
