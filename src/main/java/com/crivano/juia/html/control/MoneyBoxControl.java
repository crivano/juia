package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import com.crivano.juia.control.FieldMoney;
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

public class MoneyBoxControl {

	public static void render(Div parent, ClassAttribute col, final FieldMoney vi) {
		Section section = new Section(parent, new ClassAttribute(col.getAttributeValue() + " form-group")) {
			{
				new I(Utils.label(this, vi), new ClassAttribute("icon-append fa fa-dollar"));
				Input input = new Input(this, new Type("text"), new Id(vi.fld.getName()),
						new CustomAttribute("ng-model", vi.name), new CustomAttribute("ui-money-mask", "2"),
						new Name(vi.fld.getName()), new ClassAttribute("form-control isMoney"));
				if (vi.fld.isAnnotationPresent(NotNull.class))
					input.addAttributes(new CustomAttribute("ng-required", "true"));
				HtmlTemplateBuilder.addAttr(vi.getAttr(), input);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
