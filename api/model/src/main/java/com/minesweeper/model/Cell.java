package com.minesweeper.model;

public class Cell {

	public static String EMPTY_CELL = "E";

	public static String QUESTION_CELL = "Q";
	public static String FLAG_CELL = "F";

	public static String UNKNOWN_CELL = "U";
	public static String MINE_CELL = "M";

	private boolean mine;
	private String value;

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean canBeDeselected() {
		return (this.value == null || this.value.equals(QUESTION_CELL) || this.value.equals(FLAG_CELL));
	}

}
