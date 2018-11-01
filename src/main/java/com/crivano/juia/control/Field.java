package com.crivano.juia.control;

import com.crivano.juia.annotations.Global.Gender;

public abstract class Field extends Control {
	public java.lang.reflect.Field fld;
	public int colXS;
	public int colS;
	public int colM;
	public int colL;
	public int colXL;

	public String[] attr;
	public String[] attrContainer;
	public String[] attrItem;

	public int size;
	public boolean wrap;

	public String singular;
	public String plural;
	public Gender gender;
	public String locator;

	public String value;
}
