package com.crivano.juia;

import java.lang.reflect.Field;

import com.crivano.juia.annotations.Global.Gender;

public class ViewItem {

	public enum Type {
		ListBegin, ListEnd, CompleteBox, CheckBox, TextBox, ComboBox, DateBox, MoneyBox, Table, TableColumn, Button, SearchButton, BackButton, NewButton, fSelectButton, EditButton, DeleteButton, SaveButton, CancelButton, Menu
	}

	public enum Width {
		Huge, Big, Normal, Small
	}

	public ViewItem viRelated;
	public ViewGlue glue;
	public Field fld;
	public String name;
	public String caption;
	public String hint;
	public int colXS;
	public int colS;
	public int colM;
	public int colL;
	public int colXL;
	public boolean newRow;
	public String newFieldSet;

	public String[] attr;

	public int size;
	public boolean wrap;
	public Type type;

	public String singular;
	public String plural;
	public Gender gender;
	public String locator;

	public String mappedBy;
	public View.Kind viewKind; // of the controller to pop

	public String getPrefixedIdentifier() {
		if (fld != null)
			return "_" + fld.getName();
		if (viRelated != null && viRelated.fld != null)
			return "_" + viRelated.fld.getName();
		if (caption != null)
			return "_" + caption;
		return "";
	}
}
