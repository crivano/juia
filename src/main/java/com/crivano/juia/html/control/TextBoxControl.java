package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import com.crivano.juia.annotations.Edit;
import com.crivano.juia.annotations.EditKindEnum;
import com.crivano.juia.control.FieldText;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;

public class TextBoxControl {
	public static void render(Div parent, String col, final FieldText vi) {
		Section section = new Section(parent, new ClassAttribute(col + " form-group")) {
			{
				String tagName = "juia-text";
				Edit edit = vi.fld.getAnnotation(Edit.class);
				if (edit.kind() == EditKindEnum.TEXTAREA)
					tagName = "juia-text-area";
				CustomTag tag = new CustomTag(tagName, Utils.label(this, vi), new Id(vi.fld.getName()),
						new CustomAttribute(":value", vi.name),
//						new CustomAttribute("@input",
//								"atInput('" + (vi.name.startsWith("data.") ? vi.name.substring(5) : vi.name)
//										+ "', $event)"),
						new CustomAttribute("@input", vi.name + " = $event.target.value; proxify()"),
						new Name(vi.fld.getName()), new CustomAttribute("autocomplete", "off"));
				if (vi.fld.isAnnotationPresent(NotNull.class))
					tag.addAttributes(new CustomAttribute("required", "true"));
				HtmlTemplateBuilder.addAttr(vi.attr, tag);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
