package com.crivano.juia;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import com.crivano.jbiz.IEnum;
import com.crivano.juia.annotations.Browse;
import com.crivano.juia.annotations.Detail;
import com.crivano.juia.annotations.DetailGroup;
import com.crivano.juia.annotations.Edit;
import com.crivano.juia.annotations.EditKindEnum;
import com.crivano.juia.annotations.FieldSet;
import com.crivano.juia.annotations.Global;
import com.crivano.juia.annotations.GlobalClass;
import com.crivano.juia.annotations.Search;
import com.crivano.juia.annotations.Show;
import com.crivano.juia.annotations.ShowGroup;
import com.crivano.juia.control.Control;
import com.crivano.juia.control.FieldCheck;
import com.crivano.juia.control.FieldCombo;
import com.crivano.juia.control.FieldComplete;
import com.crivano.juia.control.FieldDate;
import com.crivano.juia.control.FieldFile;
import com.crivano.juia.control.FieldHidden;
import com.crivano.juia.control.FieldInteger;
import com.crivano.juia.control.FieldMoney;
import com.crivano.juia.control.FieldMultipleSelect;
import com.crivano.juia.control.FieldNumeric;
import com.crivano.juia.control.FieldRefSelect;
import com.crivano.juia.control.FieldSelect;
import com.crivano.juia.control.FieldText;
import com.crivano.juia.control.Repeat;
import com.crivano.juia.control.Sidebar;
import com.crivano.juia.control.TableColumn;
import com.crivano.juia.control.Topic;

public class AnnotationViewBuilder extends ViewBuilder {
	public void buildView(String prefix, Object o, View.Kind kind, boolean fFrontView) {
		view.setKind(kind);
		Global juiaGlobal = o.getClass().getAnnotation(Global.class);
		if (juiaGlobal != null) {
			view.setSingular(juiaGlobal.singular());
			view.setPlural(juiaGlobal.plural());
			view.setGender(juiaGlobal.gender());
			view.setInactivable(juiaGlobal.inactivable());
		}
		addViewItemsForObject(view, prefix, kind, false, o.getClass());
		if (kind == View.Kind.ShowView) {
			Sidebar sb = new Sidebar();
			addViewItemsForObject(sb, prefix, kind, true, o.getClass());
			view.getControls().add(sb);
		}
		switch (kind) {
		case SearchView:
			addSearchItems(fFrontView);
			break;
		case ShowView:
			addShowItems(fFrontView);
			break;
		case EditView:
			addEditItems(juiaGlobal.deletable(), juiaGlobal.inactivable(), fFrontView);
			break;
		}
	}

	public static boolean isSkipShow(Class clazz) {
		List<Class> classes = getClassHierarchy(clazz);
		return isSkipShow(classes);
	}

	private static boolean isSkipShow(List<Class> classes) {
		boolean skipShow = true;
		for (Class clazz : classes) {
			Field fieldlist[] = clazz.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				Field fld = fieldlist[i];
				fld.setAccessible(true);
				Show juiaShow = fld.getAnnotation(Show.class);
				Detail juiaDetail = fld.getAnnotation(Detail.class);
				if (juiaShow == null && juiaDetail == null)
					continue;
				skipShow = false;
				break;
			}
		}
		return skipShow;
	}

	private void addViewItemsForObject(ControlContainer container, String prefix, View.Kind kind, boolean detail,
			Class originalClass) {
		List<Class> classes = getClassHierarchy(originalClass);

		// Verifica se existe alguma anotação de @Show ou @Detail, para saber se
		// deve ou não passar pela página de show
		boolean skipShow = isSkipShow(classes);

		for (Class clazz : classes) {
			Field fieldlist[] = clazz.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				Field fld = fieldlist[i];
				fld.setAccessible(true);
				Search juiaSearch = fld.getAnnotation(Search.class);
				Show juiaShow = fld.getAnnotation(Show.class);
				Edit juiaEdit = fld.getAnnotation(Edit.class);
				Detail juiaDetail = fld.getAnnotation(Detail.class);
				if (juiaSearch == null && juiaEdit == null && juiaShow == null && juiaDetail == null)
					continue;

				// Embedded object
				//
				if (kind == View.Kind.EditView && juiaEdit != null) {
					if (!isManyToOne(fld) && !fld.getType().isEnum()
							&& fld.getType().isAnnotationPresent(Global.class)) {
						int nextItem = container.getControls().size();
						addViewItemsForObject(container, prefix + fld.getName() + ".", kind, detail, fld.getType());

						// New group
						if (container.getControls().size() > nextItem) {
							FieldSet juiaFieldSet = fld.getAnnotation(FieldSet.class);
							Control vg = container.getControls().get(nextItem);
							if (vg.newGroup == null && juiaFieldSet != null) {
								vg.newGroup = juiaFieldSet.caption();
								vg.strongGroup = juiaFieldSet.strong();
								vg.attrGroup = juiaFieldSet.attr();
							}
						}
						continue;
					}
				}

				// Embedded List, build a repeat component on a EditView
				//
				if (kind == View.Kind.EditView && juiaEdit != null && List.class.isAssignableFrom(fld.getType())) {
					Repeat repeat = new Repeat(fld.getName());
					repeat.name = prefix + fld.getName();
					FieldSet juiaFieldSet = fld.getAnnotation(FieldSet.class);
					if (juiaFieldSet != null) {
						repeat.newGroup = juiaFieldSet.caption();
						repeat.strongGroup = juiaFieldSet.strong();
						repeat.attrGroup = juiaFieldSet.attr();
					}
					container.getControls().add(repeat);
					Class clazzList = (Class) ((ParameterizedType) fld.getGenericType()).getActualTypeArguments()[0];
					Global juiaGlobal = (Global) clazzList.getAnnotation(Global.class);
					if (juiaGlobal != null) {
						repeat.setSingular(juiaGlobal.singular());
						repeat.setPlural(juiaGlobal.plural());
						repeat.setGender(juiaGlobal.gender());
						if (repeat.newGroup == null) {
							repeat.newGroup = juiaGlobal.plural();
						}
					}
					int nextItem = container.getControls().size();
					addViewItemsForObject(repeat, fld.getName() + "Item.", kind, detail, clazzList);
					continue;
				}
				Control control = addViewItem(prefix, kind, detail, fld, juiaSearch, juiaEdit, juiaShow, juiaDetail,
						skipShow);
				if (control != null)
					container.getControls().add(control);
			}
		}
	}

	private boolean isManyToOne(Field fld) {
		boolean manyToOne = false;
		//System.out.println("*** " + fld.getName());
		for (Annotation a : fld.getDeclaredAnnotations()) {
			//System.out.println(a.annotationType().getSimpleName());
			if (a.annotationType().getSimpleName().equals("ManyToOne"))
				manyToOne = true;
		}
		return manyToOne;
	}

	protected static List<Class> getClassHierarchy(Class baseClass) {
		List<Class> classes = new ArrayList<Class>();
		for (Class clazz = baseClass; clazz != Object.class; clazz = clazz.getSuperclass()) {
			classes.add(0, clazz);
		}
		return classes;
	}

	private Control addViewItem(String prefix, View.Kind kind, boolean detail, Field fld, Search juiaSearch,
			Edit juiaEdit, Show juiaShow, Detail juiaDetail, boolean skipShow) {
		Control vg;
		if (juiaSearch == null && juiaEdit == null && juiaShow == null && juiaDetail == null)
			return null;

		FieldSet juiaFieldSet = fld.getAnnotation(FieldSet.class);
		Browse juiaBrowse = fld.getAnnotation(Browse.class);

		// Get juiaGlobal for generic type
		Global juiaGlobal = null;
		GlobalClass juiaGlobalClass = fld.getAnnotation(GlobalClass.class);
		if (juiaGlobalClass != null) {
			try {
				juiaGlobal = (Global) (this.getClass().getClassLoader().loadClass(juiaGlobalClass.value()))
						.getAnnotation(Global.class);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("can't load @GlobalClass: " + juiaGlobalClass.value(), e);
			}
		} else {
			juiaGlobal = fld.getType().getAnnotation(Global.class);

			Type type = fld.getGenericType();
			if (juiaGlobal == null && type instanceof ParameterizedType) {
				ParameterizedType pType = (ParameterizedType) type;
				Type arg0 = pType.getActualTypeArguments()[0];
				if (arg0 instanceof Class)
					juiaGlobal = (Global) ((Class) arg0).getAnnotation(Global.class);
			}
		}

		if (kind == View.Kind.SearchView) {
			vg = new TableColumn(fld.getName(), skipShow);
		} else if (kind == View.Kind.ShowView) {
			if (!detail)
				vg = new Topic();
			else
				vg = new Topic();
		} else if (isAnnotatedWithId(fld)) {
			vg = new FieldHidden();
		} else if (juiaEdit != null && juiaEdit.kind() == EditKindEnum.FILE) {
			vg = new FieldFile();
		} else if ("Ref".equals(fld.getType().getSimpleName()) || isManyToOne(fld)) {
			if (juiaEdit != null && juiaEdit.kind() == EditKindEnum.SELECT)
				vg = new FieldRefSelect(juiaEdit.init(), juiaEdit.options());
			else
				vg = new FieldComplete();
		} else if (IEnum.class.isAssignableFrom(fld.getType())) {
			vg = new FieldCombo();
		} else if (Set.class.isAssignableFrom(fld.getType())) {
			vg = new FieldMultipleSelect();
		} else if (fld.getType() == LocalDate.class) {
			vg = new FieldDate();
		} else if (fld.getType() == Money.class) {
			vg = new FieldMoney();
		} else if (fld.getType() == String.class) {
			if (juiaEdit != null && juiaEdit.kind() == EditKindEnum.SELECT)
				vg = new FieldSelect(juiaEdit.init(), juiaEdit.options());
			else
				vg = new FieldText();
		} else if (fld.getType() == Double.class || fld.getType() == Float.class) {
			vg = new FieldNumeric();
		} else if (fld.getType() == Long.class || fld.getType() == Integer.class) {
			vg = new FieldInteger();
		} else if (fld.getType().isPrimitive()) {
			if (fld.getType() == boolean.class || fld.getType() == Boolean.class)
				vg = new FieldCheck();
			else
				vg = new FieldText();
		} else {
			vg = new FieldCombo();
		}

		vg.name = prefix + fld.getName();
		switch (kind) {
		case SearchView:
			if (juiaSearch == null)
				return null;
			vg.caption = juiaSearch.caption();
			vg.hint = juiaSearch.hint();
			break;
		case EditView:
			if (juiaEdit == null)
				return null;
			vg.caption = juiaEdit.caption();
			vg.hint = juiaEdit.hint();
			if (vg instanceof com.crivano.juia.control.Field) {
				com.crivano.juia.control.Field f = (com.crivano.juia.control.Field) vg;
				f.fld = fld;

				// Set the width
				f.colXS = juiaEdit.colXS();
				f.colS = juiaEdit.colS();
				f.colM = juiaEdit.colM();
				f.colL = juiaEdit.colL();
				f.colXL = juiaEdit.colXL();

				setFieldGlobals(f, juiaGlobal);
				f.attr = juiaEdit.attr();
				f.attrContainer = juiaEdit.attrContainer();
				f.attrItem = juiaEdit.attrItem();
			}
			vg.newRow = juiaEdit.newRow();
			if (juiaFieldSet != null) {
				vg.newGroup = juiaFieldSet.caption();
				vg.strongGroup = juiaFieldSet.strong();
				vg.attrGroup = juiaFieldSet.attr();
			}
			if ("<none>".equals(vg.newGroup))
				vg.newGroup = null;

			break;
		case ShowView:
			if (!detail) {
				if (juiaShow == null)
					return null;
				vg.caption = juiaShow.caption();
				vg.hint = juiaShow.hint();
				if (vg instanceof com.crivano.juia.control.Field) {
					com.crivano.juia.control.Field f = (com.crivano.juia.control.Field) vg;
					f.fld = fld;

					f.value = juiaShow.value();
					setFieldGlobals(f, juiaGlobal);
					f.attr = juiaShow.attr();
				}
				ShowGroup juiaShowGroup = fld.getAnnotation(ShowGroup.class);
				if (juiaShowGroup != null) {
					vg.newGroup = juiaShowGroup.caption();
					vg.newRowGroup = juiaShowGroup.newRow();
					// Set the width
					vg.colXSGroup = juiaShowGroup.colXS();
					vg.colSGroup = juiaShowGroup.colS();
					vg.colMGroup = juiaShowGroup.colM();
					vg.colLGroup = juiaShowGroup.colL();
					vg.colXLGroup = juiaShowGroup.colXL();
				}
				if ("<none>".equals(vg.newGroup))
					vg.newGroup = null;
			} else {
				if (juiaDetail == null)
					return null;
				vg.caption = juiaDetail.caption();
				vg.hint = juiaDetail.hint();
				if (vg instanceof com.crivano.juia.control.Field) {
					com.crivano.juia.control.Field f = (com.crivano.juia.control.Field) vg;
					f.fld = fld;
					f.colXS = 12;
					setFieldGlobals(f, juiaGlobal);
					f.attr = juiaDetail.attr();
				}
				vg.newRow = true;
				DetailGroup juiaDetailGroup = fld.getAnnotation(DetailGroup.class);
				if (juiaDetailGroup != null)
					vg.newGroup = juiaDetailGroup.caption();
				if ("<none>".equals(vg.newGroup))
					vg.newGroup = null;
			}

			break;
		}

		// Set the caption
		vg.caption = fillWithDefaultCaption(vg.caption, juiaEdit, juiaSearch, juiaBrowse, fld);

		// Set the hint
		if (vg.hint == null)
			vg.hint = "";
		if (vg.hint.equals("") && juiaEdit != null)
			vg.hint = juiaEdit.hint();
		if (vg.hint.equals("") && juiaSearch != null)
			vg.hint = juiaSearch.hint();
		if (vg.hint.equals("") && juiaBrowse != null)
			vg.hint = juiaBrowse.hint();

		return vg;
	}

	protected void setFieldGlobals(com.crivano.juia.control.Field f, Global juiaGlobal) {
		if (juiaGlobal != null) {
			f.singular = juiaGlobal.singular();
			f.plural = juiaGlobal.plural();
			f.gender = juiaGlobal.gender();
			f.locator = juiaGlobal.locator();
		}
	}

	static public String fillWithDefaultCaption(String sActual, Edit juiaEdit, Search juiaSearch, Browse juiaBrowse,
			Field fld) {
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

	public static boolean isAnnotatedWithId(Field fld) {
		for (Annotation a : fld.getAnnotations()) {
			if (a.annotationType().getSimpleName().equals("Id")) {
				return true;
			}
		}
		return false;
	}
}
