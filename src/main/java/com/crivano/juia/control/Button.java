package com.crivano.juia.control;

public class Button extends Control {
	String caption;

	public Button(String caption) {
		this.caption = caption;
	}

	@Override
	public String toString() {
		return "{\"control\":\"Button\",\"caption\":\"" + this.caption + "\"}";
	}
}
