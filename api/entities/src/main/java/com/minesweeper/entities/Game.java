package com.minesweeper.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.minesweeper.enums.GameStatus;

@Entity(name = "game")
public class Game extends BaseEntity {

	@Column(name = "game_columns")
	private int columns;

	@Column(name = "mines")
	private int mines;

	@Column(name = "game_rows")
	private int rows;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private GameStatus status;

	@Column(name = "data", nullable = false, columnDefinition = "MEDIUMTEXT")
	private String data;

	@Column(name = "time_spent")
	private Long timeSpent;

	public Long getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(Long timeSpent) {
		this.timeSpent = timeSpent;
	}

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

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
