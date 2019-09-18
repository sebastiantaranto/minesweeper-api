package com.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GameData {

	private Cell[][] cells;
	private int discoveredCells;

	public int getDiscoveredCells() {
		return discoveredCells;
	}

	public void setDiscoveredCells(int discoveredCells) {
		this.discoveredCells = discoveredCells;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

}
