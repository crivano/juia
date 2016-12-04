package com.crivano.juia;

import java.util.ArrayList;

import com.crivano.juia.annotations.Global;

public class View {

	public enum Kind {
		SearchView, EditView, ActionView
	}

	private String singular;
	private String plural;
	private Global.Gender gender;

	private ArrayList<ViewItem> alViewGlue;
	private Kind kind;

	public View() {
		super();
		this.alViewGlue = new ArrayList<ViewItem>();
	}

	public ArrayList<ViewItem> getViewGlueList() {
		return alViewGlue;
	}

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

}
