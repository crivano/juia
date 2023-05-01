package com.crivano.juia.control;

import com.crivano.juia.annotations.Global.Gender;
import com.crivano.juia.html.Utils;

public abstract class Field extends Control {
	public java.lang.reflect.Field fld;
	public int colXS;
	public int colS;
	public int colM;
	public int colL;
	public int colXL;

	public String[] attr;
	public String[] getAttr() {
		if (attrContainer == null)
			return attr;
//		for (String s : attrContainer) {
//			if (s.startsWith("ng-show=")) {
//				String[] expr = s.split("=", 2);
//				return Utils.append(attr, "ng-reset-on=!(" + expr[1] + ")");
//			} else if (s.startsWith("ng-hide=")) {
//				String[] expr = s.split("=", 2);
//				return Utils.append(attr, "ng-reset-on=(" + expr[1] + ")");
//			}
//		}
		return attr;
	}

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
}
