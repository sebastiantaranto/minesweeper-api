package com.minesweeper.bl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.easymock.EasyMock;
import org.junit.Test;

import com.minesweeper.dao.GameDao;
import com.minesweeper.entities.Game;
import com.minesweeper.enums.GameStatus;
import com.minesweeper.model.GameData;
import com.minesweeper.request.NewGameRequest;
import com.minesweeper.response.GameResponse;
import com.minesweeper.services.GameDataService;

public class GameBusinessLogicTest {

	private static final int ROWS = 3;
	private static final int MINES = 8;
	private static final int COLUMNS = 3;

	/***
	 * Check that a Game entity is created using the attributes in the request
	 * Return any new game in the mock as it doesn't matter for the test
	 */
	@Test
	public void testNewGameCreation() {
		GameBusinessLogic bl = new GameBusinessLogic();
		bl.setGameDataService(new GameDataService());
		GameDao daoMock = EasyMock.createMock(GameDao.class);
		bl.setGameDao(daoMock);

		NewGameRequest request = new NewGameRequest();
		request.setColumns(COLUMNS);
		request.setRows(ROWS);
		request.setMines(MINES);

		EasyMock.expect(daoMock.updateGameData(EasyMock.anyObject(Game.class), EasyMock.anyObject(GameData.class)))
				.andReturn(new Game());
		EasyMock.replay(daoMock);
		GameResponse game = bl.newGame(request);

		assertEquals(COLUMNS, game.getColumns());
		assertEquals(MINES, game.getMines());
		assertEquals(ROWS, game.getRows());
		assertEquals(GameStatus.ACTIVE, game.getStatus());
		assertNull(game.getTimeSpent());

	}
}
