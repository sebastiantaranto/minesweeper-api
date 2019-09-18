package com.minesweeper.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.minesweeper.entities.Game;

@Component
public interface GameRepository extends JpaRepository<Game, Long> {

}
