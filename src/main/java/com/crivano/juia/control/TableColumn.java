package com.crivano.juia.control;

public class TableColumn extends Control {
	public String fieldName;
	public boolean skipShow;

	public TableColumn(String fieldName, boolean skipShow) {
		this.fieldName = fieldName;
		this.skipShow = skipShow;
	}

}
