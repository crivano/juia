package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import com.crivano.juia.annotations.Edit;
import com.crivano.juia.control.FieldTime;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.AbstractHtml;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;

public class TimeBoxControl {
	public static void render(Div parent, ClassAttribute col, final FieldTime vi) {
		Section section = new Section(parent, new ClassAttribute(col.getAttributeValue() + " form-group")) {
			{
				Label lbl = Utils.label(this, vi);
				AbstractHtml input;
				Edit edit = vi.fld.getAnnotation(Edit.class);
				Type type = new Type("text");
				input = new Input(lbl, type, new ClassAttribute("form-control"), new Id(vi.fld.getName()),
						new CustomAttribute("ng-model", vi.name), new Name(vi.fld.getName()),
						new CustomAttribute("ui-time-mask", "short"));
				if (vi.fld.isAnnotationPresent(NotNull.class))
					input.addAttributes(new CustomAttribute("ng-required", "true"));

				HtmlTemplateBuilder.addAttr(vi.attr, input);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
