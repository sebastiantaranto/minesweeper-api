package com.minesweeper.bl;

public interface Constants {
	String API_PATH = "/api";
	String VERSION_PATH = "/v1";
	String DEFAULT_PATH = API_PATH + VERSION_PATH;
	String ERROR_CELL_ALREADY_DISCOVERED = "The requested cell was already discovered";
	String ERROR_CELL_NOT_TAGGED = "The requested cell is not tagged";
	String ERROR_CELL_CANNOT_TAGGED = "The requested cell was cannot be tagged";
}
