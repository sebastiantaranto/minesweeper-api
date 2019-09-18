package com.minesweeper.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.minesweeper.entities.Game;
import com.minesweeper.enums.GameStatus;
import com.minesweeper.response.GameResponse;

public class GameEntityToGameResponseTest {

	private static final GameEntityToGameResponse CONVERTER = new GameEntityToGameResponse();
	private static final Long TIME_SPENT = 2L;
	private static final int ROWS = 3;
	private static final int MINES = 8;
	private static final Date NOW = new Date();
	private static final int COLUMNS = 3;
	private static final Long ID = 1L;
	private static final String DATA = "{\"cells\":[[{\"value\":0,\"mine\":true,\"status\":\"U\"},{\"value\":0,\"mine\":true,\"status\":\"U\"},{\"value\":0,\"mine\":true,\"status\":\"U\"}],[{\"value\":0,\"mine\":true,\"status\":\"U\"},{\"value\":0,\"mine\":true,\"status\":\"U\"},{\"value\":0,\"mine\":true,\"status\":\"U\"}],[{\"value\":2,\"mine\":false,\"status\":\"K\"},{\"value\":0,\"mine\":true,\"status\":\"U\"},{\"value\":0,\"mine\":true,\"status\":\"U\"}]],\"discoveredCells\":1}";

	@Test
	public void testConverterWithFullAttributes() {

		Game game = new Game();
		game.setColumns(COLUMNS);
		game.setCreated(NOW);
		game.setData(DATA);
		game.setId(ID);
		game.setMines(MINES);
		game.setModified(NOW);
		game.setRows(ROWS);
		game.setStatus(GameStatus.ACTIVE);
		game.setTimeSpent(TIME_SPENT);

		GameResponse rsp = CONVERTER.apply(game);

		assertEquals(COLUMNS, rsp.getColumns());
		assertEquals(NOW, rsp.getCreated());
		assertEquals(3, rsp.getData().getCells().length);
		assertEquals(ID, rsp.getId());
		assertEquals(MINES, rsp.getMines());
		assertEquals(NOW, rsp.getModified());
		assertEquals(ROWS, rsp.getRows());
		assertEquals(GameStatus.ACTIVE, rsp.getStatus());
		assertEquals(TIME_SPENT, rsp.getTimeSpent());
		assertTrue(rsp.getData().getCells()[0][0].isMine());
	}
}
