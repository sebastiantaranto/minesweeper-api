package com.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Cell {

	public static String EMPTY_CELL = "E";

	public static String QUESTION_CELL = "Q";
	public static String FLAG_CELL = "F";

	public static String UNKNOWN_CELL = "U";
	public static String KNOWN_CELL = "K";

	private boolean mine;
	private String status;
	private int value;

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean canBeDeselected() {
		return (this.status == null || this.status.equals(QUESTION_CELL) || this.status.equals(FLAG_CELL));
	}

}
