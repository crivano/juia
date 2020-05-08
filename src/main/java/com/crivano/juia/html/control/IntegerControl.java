package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import com.crivano.juia.control.FieldInteger;
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
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;

public class IntegerControl {
	public static void render(Div parent, String col, final FieldInteger vi) {
		Section section = new Section(parent, new ClassAttribute(col + " form-group")) {
			{
				CustomTag tag = new CustomTag("juia-integer", Utils.label(this, vi), new Id(vi.fld.getName()),
						new CustomAttribute(":value", vi.name),
						new CustomAttribute("@input",
								vi.name + " = $event.target.value; proxify()"),
						new Name(vi.fld.getName()), new CustomAttribute("autocomplete", "off"));
				if (vi.fld.isAnnotationPresent(NotNull.class))
					tag.addAttributes(new CustomAttribute("required", "true"));
				HtmlTemplateBuilder.addAttr(vi.attr, tag);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}

}
