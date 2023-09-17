package com.crivano.juia.html.control;

import java.lang.annotation.Annotation;

import javax.validation.constraints.NotNull;

import com.crivano.juia.annotations.Global.Gender;
import com.crivano.juia.control.FieldComplete;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;

public class CompleteBoxControl {

	public static void render(Div parent, ClassAttribute col, final FieldComplete vi) {
		Section section = new Section(parent, new ClassAttribute(col.getAttributeValue() + " form-group")) {
			protected String dadosDoModelo(final FieldComplete vi) {
				boolean f = false;
				for (Annotation a : vi.fld.getAnnotations()) {
					if (a.getClass().getSimpleName().equals("FullSerialization")) {
						f = true;
						break;
					}
				}

				if (vi.name.contains(".")) {
					// Split no último "."
					int i = vi.name.lastIndexOf(".");
					String[] split =  {vi.name.substring(0, i), vi.name.substring(i+1)};
//					System.out.println(vi.name);
//					String[] split = vi.name.split("\\.", 2);
					return "{context:" + split[0] + ", variable: '" + split[1] + "', full:" + f + "}";
				} else
					return "{variable: " + vi.name + "}";
			}

			{
				Div div = new Div(Utils.label(this, vi), new CustomAttribute("angucomplete-alt"),
						new CustomAttribute("placeholder", "Pesquisar " + vi.singular),
						new CustomAttribute("pause", "200"), new CustomAttribute("minlength", "1"),
						new CustomAttribute("input-class", "form-control form-control-small"),
						new CustomAttribute("match-class", "highlight"),
						new CustomAttribute("template-url", "/my-custom-template.html"),
						new CustomAttribute("selected-object", "selectedObject"),
						new CustomAttribute("selected-object-data", dadosDoModelo(vi)),
						new CustomAttribute("initial-value", vi.name + ".originalObject"),
						new CustomAttribute("remote-url", "app/" + vi.locator + "/buscar/"),
						new CustomAttribute("text-searching", "Pesquisando " + vi.plural + "..."),
						new CustomAttribute("text-no-results",
								"Não encontrei nenhum" + (vi.gender == Gender.SHE ? "a" : "") + " " + vi.singular
										+ "."),
						new CustomAttribute("focus-first", "true"),
						new CustomAttribute("remote-url-data-field", "list"),
						new CustomAttribute("title-field", "firstLine"),
						new CustomAttribute("description-field", "secondLine"),
						new ClassAttribute("form-control angucomplete-ctrl"), new Id(vi.fld.getName()),
						new Name(vi.fld.getName()));

				if (vi.fld.isAnnotationPresent(NotNull.class))
					div.addAttributes(new CustomAttribute("field-required", "true"));

				HtmlTemplateBuilder.addAttr(vi.getAttr(), div, "__FIELD_NAME__", vi.name);

			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section, "__FIELD_NAME__", vi.name);
	}
}
