package com.crivano.juia.control;

import java.util.ArrayList;

import com.crivano.juia.ControlContainer;
import com.crivano.juia.annotations.Global;

public class Repeat extends Control implements ControlContainer {
	private ArrayList<Control> controls = new ArrayList<Control>();
	public String fldName;
	private String singular;
	private String plural;
	private Global.Gender gender;

	public Repeat(String fldName) {
		this.fldName = fldName;
	}

	public String getFldName() {
		return fldName;
	}

	public void setFldName(String fldName) {
		this.fldName = fldName;
	}

	public String getSingular() {
		return singular;
	}

	public void setSingular(String singular) {
		this.singular = singular;
	}

	public String getPlural() {
		return plural;
	}

	public void setPlural(String plural) {
		this.plural = plural;
	}

	public Global.Gender getGender() {
		return gender;
	}

	public void setGender(Global.Gender gender) {
		this.gender = gender;
	}

	public ArrayList<Control> getControls() {
		return controls;
	}

	public void setControls(ArrayList<Control> controls) {
		this.controls = controls;
	}
}
