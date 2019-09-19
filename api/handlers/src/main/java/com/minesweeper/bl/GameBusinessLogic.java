package com.minesweeper.bl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.minesweeper.converters.GameDataJsonConverter;
import com.minesweeper.converters.GameEntityToGameResponse;
import com.minesweeper.dao.GameDao;
import com.minesweeper.dao.GameRepository;
import com.minesweeper.entities.Game;
import com.minesweeper.enums.GameStatus;
import com.minesweeper.exceptions.BadRequestException;
import com.minesweeper.exceptions.EntityNotFound;
import com.minesweeper.model.Cell;
import com.minesweeper.model.GameData;
import com.minesweeper.request.NewGameRequest;
import com.minesweeper.request.TagCellRequest;
import com.minesweeper.response.GameResponse;
import com.minesweeper.services.GameDataService;

@Component
public class GameBusinessLogic {

	private static final GameEntityToGameResponse GAME_ENTITY_CONVERTER = new GameEntityToGameResponse();
	private static final GameDataJsonConverter GAME_DATA_CONVERTER = new GameDataJsonConverter();
	private static final String INVALID_TAG_TEXT = "Invalid tag name. Valid tag names are " + Cell.FLAG_CELL + " AND "
			+ Cell.QUESTION_CELL;

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private GameDao gameDao;
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

		gameDao.updateGameData(game, gameData);
		return GAME_ENTITY_CONVERTER.apply(game);

	}

	@Transactional
	public GameResponse checkCell(Long id, Integer row, Integer column) {
		Game entity = gameRepository.findById(id).orElseThrow(() -> new EntityNotFound());

		validateCellAndGame(row, column, entity);

		GameData gameData = GAME_DATA_CONVERTER.apply(entity.getData());
		gameDataService.checkCell(entity, gameData, row, column);

		calculateSpentTime(entity);
		gameDao.updateGameData(entity, gameData);
		return GAME_ENTITY_CONVERTER.apply(entity);
	}

	@Transactional
	public GameResponse untagCell(Long id, Integer row, Integer column) {
		Game entity = gameRepository.findById(id).orElseThrow(() -> new EntityNotFound());
		validateCellAndGame(row, column, entity);

		GameData gameData = GAME_DATA_CONVERTER.apply(entity.getData());
		gameDataService.untagCell(gameData, row, column);

		gameDao.updateGameData(entity, gameData);
		return GAME_ENTITY_CONVERTER.apply(entity);
	}

	@Transactional
	public GameResponse tagCell(Long id, Integer row, Integer column, TagCellRequest request) {
		Game entity = gameRepository.findById(id).orElseThrow(() -> new EntityNotFound());
		validateCellAndGame(row, column, entity);

		if (!request.getTag().equals(Cell.FLAG_CELL) && !request.getTag().equals(Cell.QUESTION_CELL)) {
			throw new BadRequestException(INVALID_TAG_TEXT);
		}

		GameData gameData = GAME_DATA_CONVERTER.apply(entity.getData());
		gameDataService.tagCell(gameData, row, column, request.getTag());

		gameDao.updateGameData(entity, gameData);
		return GAME_ENTITY_CONVERTER.apply(entity);

	}

	@Transactional(readOnly = true)
	public List<GameResponse> getAllGames() {
		List<Game> games = gameRepository.findAll();
		return Lists.newArrayList(Iterables.transform(games, GAME_ENTITY_CONVERTER));
	}

	@Transactional
	public GameResponse quitGame(Long id) {
		Game entity = gameRepository.findById(id).orElseThrow(() -> new EntityNotFound());
		entity.setStatus(GameStatus.QUITTED);
		entity.setModified(new Date());
		calculateSpentTime(entity);
		gameRepository.save(entity);
		return GAME_ENTITY_CONVERTER.apply(entity);
	}

	@Transactional(readOnly = true)
	public GameResponse getGameById(Long id) {
		Game entity = gameRepository.findById(id).orElseThrow(() -> new EntityNotFound());
		return GAME_ENTITY_CONVERTER.apply(entity);
	}

	private void calculateSpentTime(Game game) {
		if (!game.getStatus().equals(GameStatus.ACTIVE)) {
			Date now = new Date();
			game.setTimeSpent(now.getTime() - game.getCreated().getTime());
		}
	}

	private void validateCellAndGame(Integer row, Integer column, Game entity) {
		if (!GameStatus.ACTIVE.equals(entity.getStatus())) {
			throw new BadRequestException("Game is no longer ACTIVE");
		}

		if (row > entity.getRows() || row < 0) {
			throw new BadRequestException("Invalid row number. Please use a number between 0 and " + entity.getRows());
		}

		if (column > entity.getColumns() || column < 0) {
			throw new BadRequestException(
					"Invalid column number. Please use a number between 0 and " + entity.getColumns());
		}
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}

	public void setGameDataService(GameDataService gameDataService) {
		this.gameDataService = gameDataService;
	}

}
