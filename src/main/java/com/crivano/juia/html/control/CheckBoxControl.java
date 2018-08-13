package com.crivano.juia.html.control;

import com.crivano.juia.control.FieldCheck;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class CheckBoxControl {
	public static void render(Div parent, ClassAttribute col,
			final FieldCheck vi) {
		Section section = new Section(parent, col) {
			{
				new Label(this, new ClassAttribute("checkbox")) {
					{
						Input input = new Input(this, new Type("checkbox"),
								new CustomAttribute("ng-model", vi.name));
						HtmlTemplateBuilder.addAttr(vi.attr, input);
						new I(this);
						new NoTag(this, vi.caption);
					}
				};
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
