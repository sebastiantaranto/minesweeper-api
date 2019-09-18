package com.minesweeper.converters;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.base.Function;
import com.minesweeper.entities.Game;
import com.minesweeper.exceptions.InternalServerErrorException;
import com.minesweeper.model.GameData;
import com.minesweeper.response.GameResponse;

public class GameEntityToGameResponse implements Function<Game, GameResponse> {

	@Override
	public GameResponse apply(Game input) {
		GameResponse rsp = new GameResponse();
		rsp.setColumns(input.getColumns());
		rsp.setCreated(input.getCreated());
		if (input.getData() != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				rsp.setData(mapper.readValue(input.getData(), GameData.class));
			} catch (Exception e) {
				throw new InternalServerErrorException("An error ocurred trying to parse the game data.");
			}
		}
		rsp.setId(input.getId());
		rsp.setMines(input.getMines());
		rsp.setModified(input.getModified());
		rsp.setRows(input.getRows());
		rsp.setStatus(input.getStatus());
		rsp.setTimeSpent(input.getTimeSpent());
		return rsp;
	}

}
