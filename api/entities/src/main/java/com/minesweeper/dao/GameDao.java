package com.minesweeper.dao;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.minesweeper.entities.Game;
import com.minesweeper.exceptions.InternalServerErrorException;
import com.minesweeper.model.GameData;

@Component
public class GameDao {

	@Autowired
	private GameRepository gameRepository;

	public Game updateGameData(Game game, GameData gameData) {
		game.setModified(new Date());
		ObjectMapper mapper = new ObjectMapper();
		try {
			game.setData(mapper.writeValueAsString(gameData));
		} catch (Exception e) {
			throw new InternalServerErrorException("There was an error trying to process the game");
		}
		return gameRepository.save(game);
	}

}
