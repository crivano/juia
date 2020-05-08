package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import com.crivano.juia.annotations.Global.Gender;
import com.crivano.juia.control.FieldSelect;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Value;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Option;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Select;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class StringSelectControl {
	public static void render(Div parent, String col, final FieldSelect vi) {
		Section section = new Section(parent, new ClassAttribute(col + " form-group")) {
			{
				CustomTag select = new CustomTag("juia-string-select", Utils.label(this, vi),
						new ClassAttribute("form-control"), new CustomAttribute(":value", vi.name),
						new CustomAttribute("@input", vi.name + " = $event.target.value; proxify()"),
						new CustomAttribute(":options", vi.getOptions()),
						new CustomAttribute("gender", vi.gender != null ? vi.gender.name() : ""));
				HtmlTemplateBuilder.addAttr(vi.attr, select);
				if (vi.fld.isAnnotationPresent(NotNull.class))
					select.addAttributes(new CustomAttribute("required", "true"));
				HtmlTemplateBuilder.addAttr(vi.attr, select);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
