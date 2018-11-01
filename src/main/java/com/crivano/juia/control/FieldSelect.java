package com.crivano.juia.control;

public class FieldSelect extends Field {
	private String init;
	private String options;

	public FieldSelect(String init, String options) {
		super();
		this.init = init;
		this.options = options;
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
}
