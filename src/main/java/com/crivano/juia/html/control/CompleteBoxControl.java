package com.crivano.juia.html.control;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.crivano.juia.annotations.Edit;
import com.crivano.juia.annotations.Global.Gender;
import com.crivano.juia.annotations.Search;
import com.crivano.juia.control.FieldComplete;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;

public class CompleteBoxControl {
	protected String dadosDoModelo(final FieldComplete vi) {
		boolean f = false;
		for (Annotation a : vi.fld.getAnnotations()) {
			if (a.getClass().getSimpleName().equals("FullSerialization")) {
				f = true;
				break;
			}
		}

		if (vi.name.contains(".")) {
			String[] split = vi.name.split("\\.", 2);
			return "{context:" + split[0] + ", variable: '" + split[1] + "', full:" + f + "}";
		} else
			return "{variable: " + vi.name + "}";
	}

	public static void render(Div parent, String col, final FieldComplete vi) {
		List<Field> flds = com.crivano.juia.util.Utils.getSearchFields(vi.fld.getType());
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (final Field fld : flds) {
			String title = null;
			if (fld.getAnnotation(Search.class) != null)
				title = com.crivano.juia.util.Utils.sorn(fld.getAnnotation(Search.class).caption());
			if (title == null && fld.getAnnotation(Edit.class) != null)
				title = com.crivano.juia.util.Utils.sorn(fld.getAnnotation(Edit.class).caption());
			if (title == null)
				title = fld.getName();
			sb.append("{title:'");
			sb.append(title);
			sb.append("',data:'");
			sb.append(fld.getName());
			sb.append("'},");
		}
		sb.append("]");

		Section section = new Section(parent, new ClassAttribute(col + " form-group")) {
			{
				CustomTag tag = new CustomTag("juia-ref", Utils.label(this, vi), new Id(vi.fld.getName()),
						new CustomAttribute(":value", vi.name),
						new CustomAttribute("@values",
								vi.name + " = $event[0]; proxify()"),
//						new Name(vi.fld.getName()), new CustomAttribute("initial-value", vi.name + ".originalObject"),
						new CustomAttribute("locator", vi.locator), new CustomAttribute("title", vi.plural),
						new CustomAttribute("placeholder",
								"Selecione " + (vi.gender == Gender.SHE ? "a" : "o") + " " + vi.singular),
//						new CustomAttribute("text-searching", "Pesquisando " + vi.plural + "..."),
//						new CustomAttribute("text-no-results", "Não encontrei nenhum"
//								+ (vi.gender == Gender.SHE ? "a" : "") + " " + vi.singular + "."),
						new CustomAttribute(":tb-columns", sb.toString()));
				if (vi.fld.isAnnotationPresent(NotNull.class))
					tag.addAttributes(new CustomAttribute("required", "true"));
				HtmlTemplateBuilder.addAttr(vi.attr, tag);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);

//			{
//				Div div = new Div(Utils.label(this, vi), new CustomAttribute("angucomplete-alt"),
//						new CustomAttribute("placeholder", "Pesquisar " + vi.singular),
//						new CustomAttribute("pause", "200"), new CustomAttribute("minlength", "1"),
//						new CustomAttribute("input-class", "form-control form-control-small"),
//						new CustomAttribute("match-class", "highlight"),
//						new CustomAttribute("template-url", "/my-custom-template.html"),
//						new CustomAttribute("selected-object", "selectedObject"),
//						new CustomAttribute("selected-object-data", dadosDoModelo(vi)),
//						new CustomAttribute("focus-first", "true"),
//						new CustomAttribute("remote-url-data-field", "list"),
//						new CustomAttribute("title-field", "firstLine"),
//						new CustomAttribute("description-field", "secondLine"),
//						new ClassAttribute("form-control angucomplete-ctrl"), new Id(vi.fld.getName()),
//						new Name(vi.fld.getName()));
//
//				if (vi.fld.isAnnotationPresent(NotNull.class))
//					div.addAttributes(new CustomAttribute("field-required", "true"));
//
//				HtmlTemplateBuilder.addAttr(vi.attr, div);
//
//			}
	}
}
