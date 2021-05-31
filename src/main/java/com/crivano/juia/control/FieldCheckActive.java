package com.crivano.juia.control;

public class FieldCheckActive extends Field {
	public FieldCheckActive(String caption, String name) {
		super();
		this.caption = caption;
		this.name = name;
	}

	@Override
	public String toString() {
		return "{\"control\":\"FieldCheckActive\"}";
	}
}
