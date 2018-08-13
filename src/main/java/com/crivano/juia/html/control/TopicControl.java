package com.crivano.juia.html.control;

import com.crivano.juia.control.Topic;
import com.webfirmframework.wffweb.tag.html.P;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Span;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class TopicControl {
	public static void render(Div parent, ClassAttribute col, final Topic vi) {
		String name = vi.name
				+ ("Ref".equals(vi.fld.getType().getSimpleName()) ? ".title"
						: "");
		String value = name;
		if (vi.value != null && vi.value.trim().length() > 0) {
			String s = vi.value;
			s = s.replace("__fieldname__", name);
			value = s;
		}

		P p = new P(parent, new CustomAttribute("ng-show", name));
		new Span(p, new ClassAttribute("topic-caption")) {
			{
				new NoTag(this, vi.caption);
			}
		};
		new NoTag(p, ": ");
		new NoTag(p, "{{" + value + " && " + value + ".title ? " + value + ".title : " + value + "}}");
	}
}
