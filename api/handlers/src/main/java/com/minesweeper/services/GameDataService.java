package com.minesweeper.services;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.minesweeper.model.Cell;
import com.minesweeper.model.GameData;

@Component
public class GameDataService {

	public GameData generateNewGameData(int rows, int columns, int mines) {

		GameData data = new GameData();
		Cell[][] cells = new Cell[rows][columns];

		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < columns; j++) {
				Cell newCell = new Cell();
				newCell.setMine(false);
				newCell.setValue(Cell.UNKNOWN_CELL);
				cells[i][j] = newCell;
			}
		}

		data.setCells(cells);

		Random rand = new Random();
		for (int i = 0; i < mines;) {
			int randomRow = rand.nextInt(rows);
			int randomColumn = rand.nextInt(columns);

			Cell randomCell = cells[randomRow][randomColumn];
			if (!randomCell.isMine()) {
				randomCell.setMine(true);
				i++;
			}
		}
		return data;
	}

}
