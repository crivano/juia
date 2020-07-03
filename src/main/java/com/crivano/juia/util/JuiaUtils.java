package com.crivano.juia.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.crivano.juia.annotations.Edit;
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

}
