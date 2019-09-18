package com.minesweeper.services;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.minesweeper.bl.Constants;
import com.minesweeper.entities.Game;
import com.minesweeper.enums.GameStatus;
import com.minesweeper.exceptions.BadRequestException;
import com.minesweeper.model.Cell;
import com.minesweeper.model.GameData;

public class GameDataServiceTest {

	private static final int ROWS = 3;
	private static final int MINES = 8;
	private static final int COLUMNS = 3;

	@Test
	public void testNewGameData() {
		GameDataService service = new GameDataService();

		GameData gameData = service.generateNewGameData(ROWS, COLUMNS, MINES);

		assertEquals(ROWS, gameData.getCells().length);
		assertEquals(COLUMNS, gameData.getCells()[0].length);

		int mines = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				Cell cell = gameData.getCells()[i][j];
				if (cell.isMine()) {
					mines++;
				}
			}
		}
		assertEquals(MINES, mines);
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testTagCellShouldReturnError() {
		GameData data = new GameData();
		Cell[][] cells = new Cell[1][1];
		data.setCells(cells);

		Cell cell = new Cell();
		cell.setStatus(Cell.KNOWN_CELL);
		cells[0][0] = cell;

		GameDataService service = new GameDataService();
		exceptionRule.expect(BadRequestException.class);
		exceptionRule.expectMessage(Constants.ERROR_CELL_CANNOT_TAGGED);
		service.tagCell(data, 0, 0, Cell.FLAG_CELL);
	}

	@Test
	public void testValidTagCell() {

		GameData data = new GameData();
		Cell[][] cells = new Cell[1][1];
		data.setCells(cells);

		Cell cell = new Cell();
		cell.setStatus(Cell.UNKNOWN_CELL);
		cells[0][0] = cell;

		GameDataService service = new GameDataService();
		service.tagCell(data, 0, 0, Cell.FLAG_CELL);

		assertEquals(Cell.FLAG_CELL.toString(), cell.getStatus());
	}

	/***
	 * Generate a game data full of mines
	 */
	@Test
	public void testCheckCellShouldLostIfCellIsMine() {
		GameDataService service = new GameDataService();
		Game game = new Game();
		game.setColumns(COLUMNS);
		game.setRows(ROWS);
		GameData gameData = service.generateNewGameData(ROWS, COLUMNS, COLUMNS * ROWS);

		service.checkCell(game, gameData, 0, 0);

		assertEquals(GameStatus.LOST, game.getStatus());
	}

	@Test
	public void testCheckCellShouldReturnErrorForKnownCell() {
		GameDataService service = new GameDataService();
		Game game = new Game();
		game.setColumns(COLUMNS);
		game.setRows(ROWS);
		GameData gameData = service.generateNewGameData(ROWS, COLUMNS, 0);
		gameData.getCells()[0][0].setStatus(Cell.KNOWN_CELL);

		exceptionRule.expect(BadRequestException.class);
		exceptionRule.expectMessage(Constants.ERROR_CELL_ALREADY_DISCOVERED);
		service.checkCell(game, gameData, 0, 0);

	}

	/***
	 * Generate a game data without mines
	 */
	@Test
	public void testCheckCellShouldChangeToEmptyAllCells() {
		GameDataService service = new GameDataService();
		Game game = new Game();
		game.setColumns(COLUMNS);
		game.setRows(ROWS);
		GameData gameData = service.generateNewGameData(ROWS, COLUMNS, 0);

		service.checkCell(game, gameData, 0, 0);

		int discoveredCells = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				Cell cell = gameData.getCells()[i][j];
				if (cell.getStatus().equals(Cell.EMPTY_CELL)) {
					discoveredCells++;
				}
			}
		}
		assertEquals(ROWS * COLUMNS, discoveredCells);
		assertEquals(ROWS * COLUMNS, gameData.getDiscoveredCells());

	}

	/***
	 * The first cell 0,0 should has 3 cells arount it.
	 */
	@Test
	public void testCheckCellShouldReturnCorrectValue() {
		GameDataService service = new GameDataService();
		Game game = new Game();
		game.setColumns(COLUMNS);
		game.setRows(ROWS);
		GameData gameData = service.generateNewGameData(ROWS, COLUMNS, ROWS * COLUMNS);
		gameData.getCells()[0][0].setMine(false);

		service.checkCell(game, gameData, 0, 0);

		assertEquals(3, gameData.getCells()[0][0].getValue());

	}
}
