package com.crivano.juia.html.control;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.crivano.jbiz.IEnum;
import com.crivano.juia.control.FieldMultipleSelect;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;

public class MultipleSelectControl {
	private static class IdLabel {
		String id;
		String label;
	}

	public static void render(Div parent, String col, final FieldMultipleSelect vi) {
		List<IdLabel> l = new ArrayList<>();

		Type type = vi.fld.getGenericType();
		if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			Type arg0 = pType.getActualTypeArguments()[0];
			if (Class.class.isInstance(arg0)) {
				Class en = Class.class.cast(arg0);
				if (IEnum.class.isAssignableFrom(en)) {
					for (final IEnum v : (IEnum[]) en.getEnumConstants()) {
						IdLabel idl = new IdLabel();
						idl.id = v.getCode();
						idl.label = v.getDescr();
						l.add(idl);
					}
				}
			}
		}

		Gson gson = new GsonBuilder().create();
		final String options = gson.toJson(l).replace("\"", "'");

		Section section = new Section(parent, new ClassAttribute(col + " form-group")) {
			{
				Div div = new Div(Utils.label(this, vi), new CustomAttribute("ng-dropdown-multiselect"),
						new CustomAttribute("options", options), new CustomAttribute("selected-model", vi.name),
						new CustomAttribute("ng-init", vi.name + "=[]"),
						new CustomAttribute("extra-settings",
								"{smartButtonMaxItems: 1, buttonClasses:'form-control multiselect-button', externalIdProp:'strings'}"),
						new CustomAttribute("translation-texts",
								"{checkAll:'Marcar todos', uncheckAll:'Desmarcar todos', selectionCount:'marcados', searchPlaceholder:'Pesquisar...', buttonDefaultText:'Selecionar', dynamicButtonTextSuffix:'marcados'}")) {
					{

					}
				};
				HtmlTemplateBuilder.addAttr(vi.attr, div);
				new I(this);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
