package com.crivano.juia;

import java.util.ArrayList;

import com.crivano.juia.annotations.Global;
import com.crivano.juia.control.Control;

public class View implements ControlContainer {

	public enum Kind {
		SearchView, EditView, ShowView
	}

	private String singular;
	private String plural;
	private Global.Gender gender;

	private ArrayList<Control> controls = new ArrayList<Control>();
	private Kind kind;
	private boolean inativable;

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
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

	public String getSingular() {
		return singular;
	}

	public void setSingular(String singular) {
		this.singular = singular;
	}

	public ArrayList<Control> getControls() {
		return controls;
	}

	public void setControls(ArrayList<Control> controls) {
		this.controls = controls;
	}

	public boolean getInactivable() {
		return this.inativable;
	}

	public void setInactivable(boolean inactivable) {
		this.inativable = inactivable;
	}

}
