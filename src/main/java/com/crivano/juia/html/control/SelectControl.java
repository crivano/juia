package com.crivano.juia.html.control;

import com.crivano.jbiz.IEnum;
import com.crivano.juia.control.FieldCombo;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;

public class SelectControl {
	public static void render(Div parent, String col, final FieldCombo vi) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (IEnum.class.isAssignableFrom(vi.fld.getType())) {
			for (final IEnum v : (IEnum[]) vi.fld.getType().getEnumConstants()) {
				sb.append("{id:'");
				sb.append(v.getCode());
				sb.append("',value:'");
				sb.append(v.getDescr());
				sb.append("'},");
			}
		}
		sb.append("]");

		Section section = new Section(parent, new ClassAttribute(col + " form-group")) {
			{
				Utils.label(this, vi);
				CustomTag tag = new CustomTag("juia-select", this, new Id(vi.fld.getName()),
						new CustomAttribute(":value", vi.name),
						new CustomAttribute("@input", vi.name + " = $event.target.value; proxify()"),
						new CustomAttribute("@change", vi.name + " = $event.target.value; proxify()"),
						new Name(vi.fld.getName()), new CustomAttribute(":list", sb.toString()));
				if (vi.isRequired())
					tag.addAttributes(new CustomAttribute("required", "true"));
				HtmlTemplateBuilder.addAttr(vi.attr, tag);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
