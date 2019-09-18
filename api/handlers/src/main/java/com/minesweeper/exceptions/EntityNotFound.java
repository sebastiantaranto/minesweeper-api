package com.minesweeper.exceptions;

public class EntityNotFound extends BaseException {

	private static final long serialVersionUID = 1L;

	public EntityNotFound() {
		super("El recurso solicitado no existe", 404, 404);
	}

}
