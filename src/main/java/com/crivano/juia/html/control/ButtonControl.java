package com.crivano.juia.html.control;

import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Button;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class ButtonControl {
	public static void render(final Div parent, ClassAttribute col, final String icon, final String name,
			final String repeatName) {
		Section section = new Section(parent, new ClassAttribute(col.getAttributeValue()));
		Label label = Utils.label(section, null);

		Label label2 = new Label(section, new ClassAttribute("input float-right"));

		new Button(label2, new ClassAttribute("btn btn-light"),
				new CustomAttribute("ng-click", repeatName + ".splice($index, 1);")) {
			{
				if (icon != null)
					new I(this, new ClassAttribute("fa fa-trash"));
				if (name != null)
					new NoTag(this, name);
			}
		};
	}
}
