package com.minesweeper.converters;

import org.codehaus.jackson.map.ObjectMapper;

import com.minesweeper.exceptions.InternalServerErrorException;
import com.minesweeper.model.GameData;

public class GameDataJsonConverter {

	public GameData apply(String input) {
		GameData rsp = null;
		if (input != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				rsp = mapper.readValue(input, GameData.class);
			} catch (Exception e) {
				throw new InternalServerErrorException("An error ocurred trying to parse the game data.");
			}

		}
		return rsp;
	}

}
