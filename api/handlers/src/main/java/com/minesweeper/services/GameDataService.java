package com.minesweeper.services;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.minesweeper.bl.Constants;
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

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Cell newCell = new Cell();
				newCell.setMine(false);
				newCell.setStatus(Cell.UNKNOWN_CELL);
				cells[i][j] = newCell;
			}
		}

		data.setCells(cells);

		Random random = new Random();
		for (int i = 0; i < mines;) {
			int randomRow = random.nextInt(rows);
			int randomColumn = random.nextInt(columns);

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

		if (!requestedCell.canBeTagged()) {
			throw new BadRequestException(Constants.ERROR_CELL_CANNOT_TAGGED);
		} else {
			requestedCell.setStatus(status);
		}
	}

	public void untagCell(GameData gameData, Integer row, Integer column) {
		Cell requestedCell = gameData.getCells()[row][column];
		if (!requestedCell.getStatus().equals(Cell.FLAG_CELL)
				&& !requestedCell.getStatus().equals(Cell.QUESTION_CELL)) {
			throw new BadRequestException(Constants.ERROR_CELL_NOT_TAGGED);
		} else {
			requestedCell.setStatus(Cell.UNKNOWN_CELL);
		}
	}

	public void checkCell(Game game, GameData gameData, Integer row, Integer column) {
		Cell requestedCell = gameData.getCells()[row][column];
		if (requestedCell.isMine()) {
			game.setStatus(GameStatus.LOST);
			requestedCell.setStatus(Cell.KNOWN_CELL);
		} else if (requestedCell.getStatus().equals(Cell.KNOWN_CELL)) {
			throw new BadRequestException(Constants.ERROR_CELL_ALREADY_DISCOVERED);
		} else {
			int numberOfMines = countMinesAround(game, gameData, row, column);
			if (numberOfMines > 0) {
				requestedCell.setStatus(Cell.KNOWN_CELL);
				requestedCell.setValue(numberOfMines);
				gameData.setDiscoveredCells(gameData.getDiscoveredCells() + 1);
			} else {
				requestedCell.setStatus(Cell.UNKNOWN_CELL);
				discoverEmptyCells(game, gameData, row, column);
			}
		}
		checkIfGameWasWon(game, gameData);
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

	private void checkIfGameWasWon(Game game, GameData gameData) {
		int totalCells = game.getRows() * game.getColumns();
		if (gameData.getDiscoveredCells() + game.getMines() == totalCells) {
			game.setStatus(GameStatus.WON);
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

	/***
	 * 
	 * When a empty cell is checked we have to check all the empty cells around it.
	 */
	private void discoverEmptyCells(Game game, GameData gameData, Integer row, Integer column) {
		if (cellExist(game, gameData, row, column)) {
			Cell requestedCell = gameData.getCells()[row][column];
			if (requestedCell.getStatus().equals(Cell.UNKNOWN_CELL)) {
				int numberOfMines = countMinesAround(game, gameData, row, column);
				if (numberOfMines == 0) {
					requestedCell.setStatus(Cell.EMPTY_CELL);
					gameData.setDiscoveredCells(gameData.getDiscoveredCells() + 1);
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

}
