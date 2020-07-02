package com.crivano.juia.control;

import javax.validation.constraints.NotNull;

import com.crivano.juia.annotations.Global.Gender;
import com.crivano.juia.annotations.Required;

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

	@Override
	public String toString() {
		return "{\"control\":\"Field\",\"locator\":\"" + this.locator + "\",\"singular\":\"" + this.singular + "\"}";
	}

	public boolean isRequired() {
		return fld.isAnnotationPresent(NotNull.class) || fld.isAnnotationPresent(Required.class);
	}
}
