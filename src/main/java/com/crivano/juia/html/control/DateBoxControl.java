package com.crivano.juia.html.control;

import com.crivano.juia.control.FieldDate;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;

public class DateBoxControl {
	public static void render(Div parent, ClassAttribute col, final FieldDate vi) {
		Section section = new Section(parent, col) {
			{
				Utils.label(this, vi);

				new Label(this, new CustomAttribute("for", vi.fld.getName()),
						new Title(vi.hint), new ClassAttribute("input")) {
					{
						new I(this, new ClassAttribute(
								"icon-append fa fa-calendar"));
						Input input = new Input(this, new Type("text"),
								new ClassAttribute("form-control"), new Id(
										vi.fld.getName()), new CustomAttribute(
										"ng-model", vi.name), new Name(
										vi.fld.getName()), new CustomAttribute(
										"mask", "2999-19-39"));
						HtmlTemplateBuilder.addAttr(vi.attr, input);
					}
				};
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
