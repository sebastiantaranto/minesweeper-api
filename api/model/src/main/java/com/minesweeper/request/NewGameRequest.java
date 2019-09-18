package com.minesweeper.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class NewGameRequest {

	@Min(value = 3, message = "columns should be greater than or equal to 3")
	@Max(value = 30, message = "columns should be less than or equals to 30")
	private int columns;

	@Min(value = 1, message = "mines should be greater than 0")
	private int mines;

	@Min(value = 3, message = "rows should be greater than or equal to 3")
	@Max(value = 30, message = "should be less than or equals to 30")
	private int rows;

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
