package com.crivano.juia.html.control;

import com.crivano.juia.control.FieldCheck;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;

public class CheckBoxControl {
	public static void render(Div parent, String col, final FieldCheck vi) {
		Div div = new Div(parent, new ClassAttribute(col)) {
			{
				Section section = new Section(this, new ClassAttribute("form-check form-group")) {
					{
						CustomTag tag = new CustomTag("juia-check", this, new Id(vi.fld.getName()),
								new CustomAttribute(":checked", vi.name),
								new CustomAttribute("@change",
										"atChange('" + (vi.name.startsWith("data.") ? vi.name.substring(5) : vi.name)
												+ "', $event)"),
								new Name(vi.fld.getName()), new CustomAttribute("label", vi.caption));
						HtmlTemplateBuilder.addAttr(vi.attr, tag);
					}
				};
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, div);
	}
}
