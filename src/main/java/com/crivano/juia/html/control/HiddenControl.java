package com.crivano.juia.html.control;

import com.crivano.juia.control.FieldHidden;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;

public class HiddenControl {
	public static void render(Div parent, String col, final FieldHidden vi) {
		CustomTag tag = new CustomTag("juia-hidden", parent, new Id(vi.fld.getName()),
				new CustomAttribute(":value", vi.name),
				new CustomAttribute("@input", vi.name + " = $event.target.value; proxify()"),
				new Name(vi.fld.getName()));
		HtmlTemplateBuilder.addAttr(vi.attr, tag);
	}
}