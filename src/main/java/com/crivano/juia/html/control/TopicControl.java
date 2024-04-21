package com.crivano.juia.html.control;

import com.crivano.jbiz.IEnum;
import com.crivano.juia.control.Topic;
import com.webfirmframework.wffweb.tag.html.P;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Span;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class TopicControl {
	public static void render(Div parent, ClassAttribute col, final Topic vi) {
		String name = vi.name + ("Ref".equals(vi.fld.getType().getSimpleName()) ? ".codeAndDescr" : "");
		String value = name;
		if (vi.value != null && vi.value.trim().length() > 0) {
			String s = vi.value;
			s = s.replace("__fieldname__", name);
			value = s;
		}

		P p = new P(parent, new CustomAttribute("ng-show", name));
		if (!"-".equals(vi.caption)) {
			new Span(p, new ClassAttribute("topic-caption")) {
				{
					new NoTag(this, vi.caption);
				}
			};
			new NoTag(p, ": ");
		}
		if (IEnum.class.isAssignableFrom(vi.fld.getType())) {
			StringBuilder sb = new StringBuilder();
			sb.append("{{");
			for (final IEnum v : (IEnum[]) vi.fld.getType().getEnumConstants()) {
				sb.append(value);
				sb.append(" === '");
				sb.append(v.getCode());
				sb.append("' ? ' ");
				sb.append(v.getDescr());
				sb.append("' : ");
			}
			sb.append("'-' }}");
			new NoTag(p, sb.toString());
		} else {
			new Span(p, new CustomAttribute("ng-bind-html",
					value + " && " + value + ".title ? " + value + ".title : " + value));
		}
//			new NoTag(p, "{{" + value + " && " + value + ".title ? " + value + ".title : " + value + "}}");
	}
}
