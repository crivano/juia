package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import com.crivano.juia.control.FieldNumeric;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;

public class NumericControl {
	public static void render(Div parent, ClassAttribute col, final FieldNumeric vi) {
		Section section = new Section(parent, new ClassAttribute(col.getAttributeValue() + " form-group")) {
			{
				Input input = new Input(Utils.label(this, vi), new Type("text"), new ClassAttribute("form-control"),
						new Id(vi.fld.getName()), new CustomAttribute("ng-model", vi.name), new Name(vi.fld.getName()));
				if (vi.fld.isAnnotationPresent(NotNull.class))
					input.addAttributes(new CustomAttribute("ng-required", "true"));
				HtmlTemplateBuilder.addAttr(vi.getAttr(), input, "__FIELD_NAME__", vi.name);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section, "__FIELD_NAME__", vi.name);
	}

}
