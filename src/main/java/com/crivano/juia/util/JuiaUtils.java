package com.crivano.juia.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.crivano.juia.annotations.Edit;
import com.crivano.juia.annotations.Global;
import com.crivano.juia.annotations.Search;
import com.webfirmframework.wffweb.tag.html.AbstractHtml;
import com.webfirmframework.wffweb.tag.html.attribute.core.AbstractAttribute;

public class JuiaUtils {
	public static List<Field> getSearchFields(Class<?> sourceClass) {
		List<Field> fields = new ArrayList<Field>();
		List<Class> classes = new ArrayList<Class>();
		for (Class clazz = sourceClass; clazz != Object.class; clazz = clazz.getSuperclass()) {
			classes.add(0, clazz);
		}
		for (Class clazz : classes) {
			Field fieldlist[] = clazz.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				Field fld = fieldlist[i];
				fld.setAccessible(true);
				Search juiaSearch = fld.getAnnotation(Search.class);
				if (juiaSearch == null)
					continue;
				fields.add(fld);
			}
		}
		return fields;
	}

	public static List<Field> getEditFields(Class<?> sourceClass) {
		List<Field> fields = new ArrayList<Field>();
		List<Class> classes = new ArrayList<Class>();
		for (Class clazz = sourceClass; clazz != Object.class; clazz = clazz.getSuperclass()) {
			classes.add(0, clazz);
		}
		for (Class clazz : classes) {
			Field fieldlist[] = clazz.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				Field fld = fieldlist[i];
				fld.setAccessible(true);
				Edit juiaEdit = fld.getAnnotation(Edit.class);
				if (juiaEdit == null)
					continue;
				fields.add(fld);
			}
		}
		return fields;
	}

	public static AbstractHtml findChildByAttribute(AbstractHtml e, AbstractAttribute a) {
		AbstractAttribute a2 = e.getAttributeByName(a.getAttributeName());
		if (a2 != null && a.getAttributeValue().equals(a2.getAttributeValue()))
			return e;
		for (AbstractHtml child : e.getChildren()) {
			AbstractHtml f = findChildByAttribute(child, a);
			if (f != null)
				return f;
		}
		return null;
	}

	public static String sorn(String s) {
		if (s == null || s.length() == 0)
			return null;
		return s;
	}

	public static String sore(String s) {
		if (s == null || s.length() == 0)
			return "";
		return s;
	}

	public static Date newDate() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
	}

	public static String classLocator(Class clazz) {
		String locator = null;
		Global global = (Global) clazz.getAnnotation(Global.class);
		if (global != null && global.locator() != null && !global.locator().isEmpty())
			locator = global.locator();
		else {
			locator = breakCamelCase(clazz.getSimpleName());
			locator = slugify(locator, true, false);
		}
		return locator;
	}

	private static String breakCamelCase(String name) {
		String s = name;
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			String ch = s.substring(i, i + 1);
			if (i == 0)
				ch = ch.toUpperCase();
			else if (ch.toUpperCase().equals(ch))
				ch = " " + ch;
			result += ch;
		}
		return result;
	}

	private static String removeAccents(String acentuado) {
		if (acentuado == null)
			return null;
		String temp = new String(acentuado);
		temp = temp.replaceAll("[ÃÂÁÀ]", "A");
		temp = temp.replaceAll("[ÉÈÊ]", "E");
		temp = temp.replaceAll("[ÍÌÎ]", "I");
		temp = temp.replaceAll("[ÕÔÓÒ]", "O");
		temp = temp.replaceAll("[ÛÚÙÜ]", "U");
		temp = temp.replaceAll("[Ç]", "C");
		temp = temp.replaceAll("[ãâáà]", "a");
		temp = temp.replaceAll("[éèê]", "e");
		temp = temp.replaceAll("[íìî]", "i");
		temp = temp.replaceAll("[õôóò]", "o");
		temp = temp.replaceAll("[ûúùü]", "u");
		temp = temp.replaceAll("[ç]", "c");
		return temp;
	}

	private static String slugify(String string, boolean lowercase, boolean underscore) {
		if (string == null)
			return null;
		string = string.trim();
		if (string.length() == 0)
			return null;
		string = removeAccents(string);
		// Apostrophes.
		string = string.replaceAll("([a-z])'s([^a-z])", "$1s$2");
		string = string.replaceAll("[^\\w]", "-").replaceAll("-{2,}", "-");
		// Get rid of any - at the start and end.
		string = string.replaceAll("-+$", "").replaceAll("^-+", "");

		if (underscore)
			string = string.replaceAll("-", "_");

		return (lowercase ? string.toLowerCase() : string);
	}
}
