package com.crivano.juia.biz;

import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import com.crivano.jbiz.IEntity;
import com.crivano.jbiz.IEvent;
import com.crivano.jbiz.ITag;
import com.crivano.juia.annotations.Global;
import com.crivano.juia.util.JuiaUtils;

public interface IJuiaEntity extends IEntity {

	// private String code;
	//
	// private String descr;

	default String getSelectFirstLine() {
		return null;
	}

	default String getSelectSecondLine() {
		return null;
	}

	default void onLoad() {
	}

	default void onValidate(Object validator) {
	}

	default void onPost(Object validator, IJuiaEntity loaded) {
	}

	default void onDuplicate() {
	}

	default void onView() throws Exception {
	}

	default void onDelete() {
	}

	default void beforeSave() {
		Date dt = JuiaUtils.newDate();
		if (getBegin() == null)
			setBegin(dt);
	}

	default void afterSave(boolean firstTime) {
//		atualizarReference();
	}

	default void beforeSerialize() {
	}

	default void addEvent(IEvent<?> c) {
		if (getEvent() != null) {
			((SortedSet<IEvent<?>>) getEvent()).add((IEvent<?>) c);
		}
	}

	default void removeChange(IEvent c) {
		if (getEvent() == null || c == null)
			return;
		getEvent().remove(c);
	}

	default SortedSet<IJuiaAction> getActions() {
		SortedSet<IJuiaAction> xrpActions = new TreeSet<>(new Comparator<IJuiaAction>() {
			@Override
			public int compare(IJuiaAction o1, IJuiaAction o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		addActions(xrpActions);
		return xrpActions;
	}

	default void addActions(SortedSet<IJuiaAction> set) {
	}

	default SortedSet<ITag> getTags() {
		SortedSet<ITag> set = new TreeSet<>();
		addTags(set);
		return set;
	}

	default void addTags(SortedSet<ITag> set) {
	}

//	default Set<@OneToMany IEntity>> getRefs() {
//		Set<@OneToMany IEntity>> set = new TreeSet<>();
//		addRefs(set);
//		return set;
//	}
//
//	protected void addRefs(Set<@OneToMany IEntity>> set) {
//	}
//
//	protected void atualizarReference() {
//		@OneToMany IEntity> ref = (@OneToMany IEntity>) (Ref) Ref.create(this);
//
//		Reference reference = new Reference(ref, this.getCode(), this.getDescr(), this.getTags(), this.getRefs());
//
//		Dao dao = new Dao();
//		dao.save(reference);
//	}

	default String buildCode() {
//		String prefix = getCodePrefixWithYear();
//		if (prefix == null)
//			return null;
//		int counter = DaoUtils.getProximoCodigo(prefix);
//		return prefix + String.format("%06d", counter) + getCodeSufix();
		return null;
	}

	default String getCodePrefix() {
		String prefix = this.getClass().getAnnotation(Global.class).codePrefix();
		if (prefix == null || prefix.length() == 0)
			return null;
		return prefix;
	}

	default String getCodePrefixWithYear() {
		String s = getCodePrefix();
		if (s == null)
			return null;
		String prefix = "";
		prefix += getBegin().getYear() + 1900;
		prefix += "-";
		prefix += s;
		prefix += "-";
		return prefix;
	}

	default String getCodeSufix() {
		return "";
	}

	default String getTitle() {
		return this.getClass().getAnnotation(Global.class).singular() + " " + getCode();
	}

//	default void addIndex(SortedSet<String> set) {
//		addIndex(set, getCode());
//		addIndex(set, getDescr());
//	}
//
//	default static void addIndex(SortedSet<String> set, String prefix, String value) {
//		if (prefix == null) {
//			addIndex(set, value);
//			return;
//		}
//		if (value == null)
//			return;
//		value = Reference.canonicalizar(prefix, value);
//		if (value.length() == 0)
//			return;
//		set.add(value);
//	}
//
//	default static void addIndex(SortedSet<String> set, String value) {
//		if (value == null)
//			return;
//		value = Reference.canonicalizar(value);
//		if (value.length() == 0)
//			return;
//		set.add(value);
//	}

	public static int compare(IEntity thiz, IEntity o) {
		int i = 0;
		if (thiz.getBegin() != null) {
			i = thiz.getBegin().compareTo(o.getBegin());
			if (i != 0)
				return -i;
		}
		if (thiz.getId() != null) {
			i = thiz.getId().compareTo(o.getId());
			if (i != 0)
				return i;
		}
		i = thiz.getClass().getName().compareTo(o.getClass().getName());
		if (i != 0)
			return i;
		return 0;
	}
}
