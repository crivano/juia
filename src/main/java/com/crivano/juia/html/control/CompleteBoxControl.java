package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import br.com.xrp.util.FullSerialization;

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

	public static void render(Div parent, ClassAttribute col,
			final FieldComplete vi) {
		Section section = new Section(parent, col) {
			{
				Utils.label(this, vi);

				new Label(this, new CustomAttribute("for", vi.fld.getName()),
						new Title(vi.hint), new ClassAttribute("input")) {
					{
						new I(this, new ClassAttribute(
								"icon-append fa fa-chain"));

						Div div = new Div(
								this,
								new CustomAttribute("angucomplete-alt"),
								new CustomAttribute("placeholder", "Pesquisar "
										+ vi.singular),
								new CustomAttribute("pause", "200"),
								new CustomAttribute("minlength", "1"),
								new CustomAttribute("input-class",
										"form-control form-control-small"),
								new CustomAttribute("match-class", "highlight"),
								new CustomAttribute("template-url",
										"/my-custom-template.html"),
								new CustomAttribute("selected-object",
										"selectedObject"),
								new CustomAttribute("selected-object-data",
										dadosDoModelo(vi)),
								new CustomAttribute("initial-value", vi.name
										+ ".originalObject"),
								new CustomAttribute("remote-url", "app/"
										+ vi.locator + "/buscar/"),
								new CustomAttribute("text-searching",
										"Pesquisando " + vi.plural + "..."),
								new CustomAttribute(
										"text-no-results",
										"Não encontrei nenhum"
												+ (vi.gender == Gender.SHE ? "a"
														: "") + " "
												+ vi.singular + "."),
								new CustomAttribute("focus-first", "true"),
								new CustomAttribute("remote-url-data-field",
										"list"), new CustomAttribute(
										"title-field", "firstLine"),
								new CustomAttribute("description-field",
										"secondLine"), new ClassAttribute(
										"form-control angucomplete-ctrl"),
								new Id(vi.fld.getName()), new Name(vi.fld
										.getName()));

						if (vi.fld.isAnnotationPresent(NotNull.class))
							div.addAttributes(new CustomAttribute(
									"field-required", "true"));

						HtmlTemplateBuilder.addAttr(vi.attr, div);
					}

					protected String dadosDoModelo(final FieldComplete vi) {
						if (vi.name.contains(".")) {
							String[] split = vi.name.split("\\.", 2);
							return "{context:"
									+ split[0]
									+ ", variable: '"
									+ split[1]
									+ "', full:"
									+ (vi.fld
											.getAnnotation(FullSerialization.class) != null)
									+ "}";
						} else
							return "{variable: " + vi.name + "}";
					}
				};
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
