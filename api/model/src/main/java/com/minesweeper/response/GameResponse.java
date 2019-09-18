package com.minesweeper.response;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.minesweeper.enums.GameStatus;
import com.minesweeper.model.GameData;
import com.minesweeper.serializer.JsonDateSerializer;

@JsonInclude(Include.NON_NULL)
public class GameResponse {

	private Long id;
	@JsonSerialize(using = JsonDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
	private Date created;
	@JsonSerialize(using = JsonDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
	private Date modified;
	private int columns;
	private int mines;
	private int rows;
	private GameStatus status;
	private GameData data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public GameData getData() {
		return data;
	}

	public void setData(GameData data) {
		this.data = data;
	}

}
