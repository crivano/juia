package com.crivano.juia;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import com.crivano.jbiz.IEnum;
import com.crivano.juia.annotations.Browse;
import com.crivano.juia.annotations.Edit;
import com.crivano.juia.annotations.FieldSet;
import com.crivano.juia.annotations.Global;
import com.crivano.juia.annotations.Menu;
import com.crivano.juia.annotations.Search;

public class AnnotationViewBuilder extends ViewBuilder {
	public void buildView(String prefix, Object o, View.Kind kind,
			boolean fFrontView) {
		view.setKind(kind);
		Global juiaGlobal = o.getClass().getAnnotation(Global.class);
		if (juiaGlobal != null) {
			view.setSingular(juiaGlobal.singular());
			view.setPlural(juiaGlobal.plural());
		}
		addViewItemsForObject(prefix, kind, o.getClass());
		switch (kind) {
		case SearchView:
			addSearchItems(fFrontView);
			break;
		case EditView:
			addEditItems(fFrontView);
			break;
		}
	}

	private void addViewItemsForObject(String prefix, View.Kind kind,
			Class originalClass) {
		List<Class> classes = new ArrayList<Class>();
		for (Class clazz = originalClass; clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			classes.add(0, clazz);
		}
		for (Class clazz : classes) {
			Field fieldlist[] = clazz.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				Field fld = fieldlist[i];
				if (fld.getType().isAnnotationPresent(Global.class)) {
					int nextItem = view.getViewGlueList().size();
					addViewItemsForObject(prefix + fld.getName() + ".", kind,
							fld.getType());
					if (view.getViewGlueList().size() > nextItem
							&& kind == View.Kind.EditView) {
						Edit juiaEdit = fld.getAnnotation(Edit.class);
						FieldSet juiaFieldSet = fld
								.getAnnotation(FieldSet.class);
						ViewItem vg = view.getViewGlueList().get(nextItem);
						if (vg.newFieldSet == null && juiaEdit != null
								&& juiaFieldSet != null) {
							vg.newFieldSet = juiaFieldSet.caption();
						}
					}
				} else if (Collection.class.isAssignableFrom(fld.getType())) {
					{
						ViewItem vg = new ViewItem();
						vg.name = prefix + fld.getName();
						vg.type = ViewItem.Type.ListBegin;
						view.getViewGlueList().add(vg);
					}
					Class clazzList = (Class) ((ParameterizedType) fld
							.getGenericType()).getActualTypeArguments()[0];

					int nextItem = view.getViewGlueList().size();
					addViewItemsForObject(fld.getName() + "Item.", kind,
							clazzList);
					{
						ViewItem vg = new ViewItem();
						vg.name = prefix + fld.getName();
						vg.type = ViewItem.Type.ListEnd;
						view.getViewGlueList().add(vg);
					}
				} else
					addViewItem(prefix, kind, fld);
			}
		}
	}

	private void addViewItem(String prefix, View.Kind kind, Field fld) {
		fld.setAccessible(true);
		// int mod = fld.getModifiers();
		// switch (mod) {
		// case Modifier.PUBLIC:
		// fld.getName();
		// ;

		Search juiaSearch = fld.getAnnotation(Search.class);
		FieldSet juiaFieldSet = fld.getAnnotation(FieldSet.class);
		Edit juiaEdit = fld.getAnnotation(Edit.class);
		Browse juiaBrowse = fld.getAnnotation(Browse.class);

		// Get juiaGlobal for generic type
		Global juiaGlobal = null;
		Type type = fld.getGenericType();
		if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			Type arg0 = pType.getActualTypeArguments()[0];
			if (arg0 instanceof Class)
				juiaGlobal = (Global) ((Class) arg0)
						.getAnnotation(Global.class);
		}

		ViewItem vg = new ViewItem();
		vg.name = prefix + fld.getName();
		switch (kind) {
		case SearchView:
			if (juiaSearch == null)
				return;
			vg.caption = juiaSearch.caption();
			vg.hint = juiaSearch.hint();
			vg.size = juiaSearch.size();
			vg.wrap = juiaSearch.wrap();
			break;
		case EditView:
			if (juiaEdit == null)
				return;
			vg.caption = juiaEdit.caption();
			vg.hint = juiaEdit.hint();
			vg.colXS = juiaEdit.colXS();
			vg.colS = juiaEdit.colS();
			vg.colM = juiaEdit.colM();
			vg.colL = juiaEdit.colL();
			vg.colXL = juiaEdit.colXL();
			vg.newRow = juiaEdit.newRow();
			vg.attr = juiaEdit.attr();
			if (juiaFieldSet != null)
				vg.newFieldSet = juiaFieldSet.caption();
			if ("<none>".equals(vg.newFieldSet))
				vg.newFieldSet = null;

			vg.size = juiaEdit.size();
			vg.wrap = juiaEdit.wrap();

			if (juiaGlobal != null) {
				vg.singular = juiaGlobal.singular();
				vg.plural = juiaGlobal.plural();
				vg.gender = juiaGlobal.gender();
				vg.locator = juiaGlobal.locator();
			}

			break;
		}

		vg.caption = fillWithDefaultCaption(vg.caption, juiaEdit, juiaSearch,
				juiaBrowse, fld);

		if (vg.hint == null)
			vg.hint = "";
		if (vg.hint.equals("") && juiaEdit != null)
			vg.hint = juiaEdit.hint();
		if (vg.hint.equals("") && juiaSearch != null)
			vg.hint = juiaSearch.hint();
		if (vg.hint.equals("") && juiaBrowse != null)
			vg.hint = juiaBrowse.hint();

		vg.fld = fld;

		if (kind == View.Kind.SearchView) {
			vg.type = ViewItem.Type.TableColumn;
			view.getViewGlueList().add(vg);
			return;
		}

		// if (Collection.class.isAssignableFrom(fld.getType())) {
		// vg.type = ViewItem.Type.Table;
		// vg.clazz = (Class) ((ParameterizedType) fld.getGenericType())
		// .getActualTypeArguments()[0];
		// } else
		if (Ref.class.isAssignableFrom(fld.getType())) {
			vg.type = ViewItem.Type.CompleteBox;
		} else if (IEnum.class.isAssignableFrom(fld.getType())) {
			vg.type = ViewItem.Type.ComboBox;
		} else if (fld.getType() == LocalDate.class) {
			vg.type = ViewItem.Type.DateBox;
		} else if (fld.getType() == Money.class) {
			vg.type = ViewItem.Type.MoneyBox;
		} else if (fld.getType() == String.class) {
			vg.type = ViewItem.Type.TextBox;
		} else if (fld.getType().isPrimitive()) {
			if (fld.getType() == boolean.class
					|| fld.getType() == Boolean.class)
				vg.type = ViewItem.Type.CheckBox;
			else
				vg.type = ViewItem.Type.TextBox;
		} else {
			vg.type = ViewItem.Type.ComboBox;
		}

		view.getViewGlueList().add(vg);
		if (vg.type == ViewItem.Type.Table && kind == View.Kind.EditView) {
			// addTableButtons(vg, pt.isCollectionType());
		}
	}

	static public String fillWithDefaultCaption(String sActual, Edit juiaEdit,
			Search juiaSearch, Browse juiaBrowse, Field fld) {
		String s = sActual;
		if (s == null)
			s = "";
		if (s.equals("") && juiaEdit != null)
			s = juiaEdit.caption();
		if (s.equals("") && juiaSearch != null)
			s = juiaSearch.caption();
		if (s.equals("") && juiaBrowse != null)
			s = juiaBrowse.caption();
		if (s.equals("")) {
			s = fld.getName();
			String result = "";
			for (int i = 0; i < s.length(); i++) {
				String ch = s.substring(i, i + 1);
				if (i == 0)
					ch = ch.toUpperCase();
				else if (ch.toUpperCase().equals(ch))
					ch = " " + ch;
				result += ch;
			}
			s = result;
		}

		return s;
	}

	public void makeMenu() {
		List<Class> classes = Juia.getClasses();
		// try {
		// //classes =
		// getClassesForPackage("br.ufrj.cos.mda.cafedonuts.entities");
		// classes = getClassesForPackage("entities");
		// } catch (ClassNotFoundException e) {
		// throw new Error(e);
		// }

		for (Class clazz : classes) {
			int mods = clazz.getModifiers();
			if (Modifier.isAbstract(mods))
				continue;
			Annotation[] aa = clazz.getAnnotations();
			for (Annotation ann : aa) {
				if (ann instanceof Menu) {
					Menu juiaMenu = (Menu) ann;
					String path[] = juiaMenu.caption().split(";");

					ViewItem viPrevious = null;
					for (String caption : path) {
						boolean fLast = caption == path[path.length - 1];

						if (!fLast) {
							ViewItem viSearch = null;
							for (ViewItem viOnList : view.getViewGlueList()) {
								if (viOnList.caption.equals(caption)) {
									viSearch = viOnList;
									break;
								}
							}
							if (viSearch != null) {
								viPrevious = viSearch;
								continue;
							}
						}

						ViewItem vi = new ViewItem();
						vi.caption = caption;
						vi.type = ViewItem.Type.Menu;
						vi.viRelated = viPrevious;
						viPrevious = vi;
						if (fLast) {
							vi.hint = juiaMenu.hint();
							vi.viewKind = juiaMenu.kind();
						}
						view.getViewGlueList().add(vi);
					}
				}
			}

		}

		return;
	}

}
