package com.minesweeper.services;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.minesweeper.entities.Game;
import com.minesweeper.enums.GameStatus;
import com.minesweeper.exceptions.BadRequestException;
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
				newCell.setStatus(Cell.UNKNOWN_CELL);
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

	public void tagCell(GameData gameData, Integer row, Integer column, String status) {
		Cell requestedCell = gameData.getCells()[row][column];

		if (requestedCell.getStatus().equals(Cell.KNOWN_CELL) || requestedCell.getStatus().equals(Cell.EMPTY_CELL)) {
			throw new BadRequestException("The requested cell was cannot be tagged");
		} else {
			requestedCell.setStatus(status);
		}
	}

	public void checkCell(Game game, GameData gameData, Integer row, Integer column) {
		Cell requestedCell = gameData.getCells()[row][column];

		if (requestedCell.isMine()) {
			game.setStatus(GameStatus.LOST);
		} else if (requestedCell.getStatus().equals(Cell.KNOWN_CELL)) {
			throw new BadRequestException("The requested cell was already discovered");
		} else {
			int numberOfMines = countMinesAround(game, gameData, row, column);
			if (numberOfMines > 0) {
				requestedCell.setStatus(Cell.KNOWN_CELL);
				requestedCell.setValue(numberOfMines);
			} else {
				discoverEmptyCells(game, gameData, row, column);
				requestedCell.setStatus(Cell.UNKNOWN_CELL);
			}

		}

	}

	private void discoverEmptyCells(Game game, GameData gameData, Integer row, Integer column) {
		if (cellExist(game, gameData, row, column)) {
			Cell requestedCell = gameData.getCells()[row][column];
			if (requestedCell.getStatus().equals(Cell.UNKNOWN_CELL)) {
				int numberOfMines = countMinesAround(game, gameData, row, column);
				if (numberOfMines == 0) {
					requestedCell.setStatus(Cell.EMPTY_CELL);
					discoverEmptyCells(game, gameData, row, column + 1);
					discoverEmptyCells(game, gameData, row, column - 1);
					discoverEmptyCells(game, gameData, row - 1, column + 1);
					discoverEmptyCells(game, gameData, row - 1, column - 1);
					discoverEmptyCells(game, gameData, row + 1, column + 1);
					discoverEmptyCells(game, gameData, row + 1, column--);
					discoverEmptyCells(game, gameData, row + 1, column);
					discoverEmptyCells(game, gameData, row - 1, column);
				}
			}
		}
	}

	/***
	 * 
	 * Check if there are mines in the possible 8 cells around and return the number
	 * of mines.
	 */
	private int countMinesAround(Game game, GameData gameData, Integer row, Integer column) {
		int minesAround = 0;

		minesAround += cellExistsAndIsMined(game, gameData, row, column + 1);
		minesAround += cellExistsAndIsMined(game, gameData, row, column - 1);
		minesAround += cellExistsAndIsMined(game, gameData, row - 1, column + 1);
		minesAround += cellExistsAndIsMined(game, gameData, row - 1, column - 1);
		minesAround += cellExistsAndIsMined(game, gameData, row + 1, column + 1);
		minesAround += cellExistsAndIsMined(game, gameData, row + 1, column--);
		minesAround += cellExistsAndIsMined(game, gameData, row + 1, column);
		minesAround += cellExistsAndIsMined(game, gameData, row - 1, column);

		return minesAround;
	}

	private boolean cellExist(Game game, GameData gameData, Integer row, Integer column) {
		return row >= 0 && row < game.getRows() && column >= 0 && column < game.getColumns();
	}

	/***
	 * 
	 * @return 1 if the cell exists and it is mined
	 */
	private int cellExistsAndIsMined(Game game, GameData gameData, Integer row, Integer column) {
		return (cellExist(game, gameData, row, column) && gameData.getCells()[row][column].isMine()) ? 1 : 0;
	}

}
