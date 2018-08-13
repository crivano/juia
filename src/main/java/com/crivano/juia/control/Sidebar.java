package com.crivano.juia.control;

import java.util.ArrayList;

import com.crivano.juia.ControlContainer;
import com.crivano.juia.annotations.Global;

public class Sidebar extends Control implements ControlContainer {
	private ArrayList<Control> controls = new ArrayList<Control>();

	public Sidebar() {
	}

	public String getSingular() {
		return null;
	}

	public String getPlural() {
		return null;
	}

	public Global.Gender getGender() {
		return null;
	}

	public ArrayList<Control> getControls() {
		return controls;
	}

	public void setControls(ArrayList<Control> controls) {
		this.controls = controls;
	}
}
