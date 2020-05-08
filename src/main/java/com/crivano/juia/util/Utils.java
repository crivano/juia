package com.crivano.juia.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.crivano.juia.annotations.Search;

public class Utils {
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
}
