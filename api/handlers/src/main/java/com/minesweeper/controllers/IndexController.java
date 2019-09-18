package com.minesweeper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController extends BaseController {

	@GetMapping("/")
	@Transactional
	public ResponseEntity<Object> apiInfo() {
		try {
			return ResponseEntity.ok("minesweeper");
		} catch (Exception e) {
			return processException(e);
		}
	}

}
