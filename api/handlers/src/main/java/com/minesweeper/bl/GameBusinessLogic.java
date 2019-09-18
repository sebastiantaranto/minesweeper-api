package com.minesweeper.bl;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.minesweeper.converters.GameEntityToGameResponse;
import com.minesweeper.dao.GameRepository;
import com.minesweeper.entities.Game;
import com.minesweeper.enums.GameStatus;
import com.minesweeper.exceptions.BadRequestException;
import com.minesweeper.exceptions.EntityNotFound;
import com.minesweeper.exceptions.InternalServerErrorException;
import com.minesweeper.model.GameData;
import com.minesweeper.request.NewGameRequest;
import com.minesweeper.response.GameResponse;
import com.minesweeper.services.GameDataService;

@Component
public class GameBusinessLogic {

	private static final GameEntityToGameResponse GAME_ENTITY_CONVERTER = new GameEntityToGameResponse();

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private GameDataService gameDataService;

	@Transactional
	public GameResponse newGame(NewGameRequest request) {
		long maxSquares = request.getColumns() * request.getRows();

		if (request.getMines() >= maxSquares) {
			throw new BadRequestException("Mines shoud be less than " + maxSquares);
		}

		Game game = new Game();
		game.setColumns(request.getColumns());
		game.setCreated(new Date());
		game.setMines(request.getMines());
		game.setRows(request.getRows());
		game.setStatus(GameStatus.ACTIVE);

		GameData gameData = gameDataService.generateNewGameData(request.getRows(), request.getColumns(),
				request.getMines());

		ObjectMapper mapper = new ObjectMapper();
		try {
			game.setData(mapper.writeValueAsString(gameData));
		} catch (Exception e) {
			throw new InternalServerErrorException("Game cannot be created.");
		}

		gameRepository.save(game);
		return GAME_ENTITY_CONVERTER.apply(game);

	}

	public Object checkCell(Long id, Integer row, Integer column) {
		return null;
	}

	public Object unselectCell(Long id, Integer row, Integer column) {

		return null;
	}

	public Object selectCell(Long id, Integer row, Integer column) {
		return null;
	}

	@Transactional
	public Object quitGame(Long id) {
		Game entity = gameRepository.findById(id).orElseThrow(() -> new EntityNotFound());
		entity.setStatus(GameStatus.QUITTED);
		entity.setModified(new Date());
		gameRepository.save(entity);
		return GAME_ENTITY_CONVERTER.apply(entity);
	}

	@Transactional(readOnly = true)
	public Object getGameById(Long id) {
		Game entity = gameRepository.findById(id).orElseThrow(() -> new EntityNotFound());
		return GAME_ENTITY_CONVERTER.apply(entity);
	}

}
