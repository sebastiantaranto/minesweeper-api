package com.minesweeper.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minesweeper.bl.GameBusinessLogic;
import com.minesweeper.request.NewGameRequest;
import com.minesweeper.request.TagCellRequest;

@RestController
@RequestMapping("/game")
public class GameController extends BaseController {

	@Autowired
	private GameBusinessLogic gameBusinessLogic;

	@PostMapping("")
	public ResponseEntity<Object> newGame(@Valid @RequestBody NewGameRequest request) {
		try {
			return new ResponseEntity<Object>(gameBusinessLogic.newGame(request), HttpStatus.CREATED);
		} catch (Exception e) {
			return processException(e);
		}
	}

	@GetMapping("")
	public ResponseEntity<Object> getAllGames() {
		try {
			return ResponseEntity.ok(gameBusinessLogic.getAllGames());
		} catch (Exception e) {
			return processException(e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getGameById(@PathVariable(name = "id") Long id) {
		try {
			return ResponseEntity.ok(gameBusinessLogic.getGameById(id));
		} catch (Exception e) {
			return processException(e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> quitGame(@PathVariable(name = "id") Long id) {
		try {
			gameBusinessLogic.quitGame(id);
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return processException(e);
		}
	}

	@PostMapping("/{id}/cell/{row}/{column}/tag")
	public ResponseEntity<Object> tagCell(@PathVariable(name = "id") Long id, @PathVariable(name = "row") Integer row,
			@PathVariable(name = "column") Integer column, @Valid @RequestBody TagCellRequest request) {
		try {
			return ResponseEntity.ok(gameBusinessLogic.tagCell(id, row, column, request));
		} catch (Exception e) {
			return processException(e);
		}
	}

	@DeleteMapping("/{id}/cell/{row}/{column}/tag")
	public ResponseEntity<Object> unselectCell(@PathVariable(name = "id") Long id,
			@PathVariable(name = "row") Integer row, @PathVariable(name = "column") Integer column) {
		try {
			return ResponseEntity.ok(gameBusinessLogic.untagCell(id, row, column));
		} catch (Exception e) {
			return processException(e);
		}
	}

	@PostMapping("/{id}/cell/{row}/{column}/check")
	public ResponseEntity<Object> checkCell(@PathVariable(name = "id") Long id, @PathVariable(name = "row") Integer row,
			@PathVariable(name = "column") Integer column) {
		try {
			return ResponseEntity.ok(gameBusinessLogic.checkCell(id, row, column));
		} catch (Exception e) {
			return processException(e);
		}
	}

}
