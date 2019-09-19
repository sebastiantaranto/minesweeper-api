package com.minesweeper.exceptions;

public class EntityNotFound extends BaseException {

	private static final long serialVersionUID = 1L;

	public EntityNotFound() {
		super("Entity not found.", 404);
	}

}
