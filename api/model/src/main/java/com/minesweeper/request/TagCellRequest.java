package com.minesweeper.request;

import javax.validation.constraints.NotNull;

public class TagCellRequest {

	@NotNull(message = "tag cannot be null or empty")
	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
