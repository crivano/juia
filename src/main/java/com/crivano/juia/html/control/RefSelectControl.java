package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import com.crivano.juia.annotations.Edit;
import com.crivano.juia.annotations.Global.Gender;
import com.crivano.juia.control.FieldRefSelect;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Value;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Option;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Select;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class RefSelectControl {
	public static void render(Div parent, ClassAttribute col, final FieldRefSelect vi) {
		Section section = new Section(parent, new ClassAttribute(col.getAttributeValue() + " form-group")) {
			{
				Select select = new Select(Utils.label(this, vi), new ClassAttribute("form-control"),
						new CustomAttribute("ng-model", vi.name), new CustomAttribute("ng-init", vi.getInit()),
						new CustomAttribute("ng-options", vi.getOptions()));
				HtmlTemplateBuilder.addAttr(vi.getAttr(), select);
				new I(this);
				Option option = new Option(select, new Value(""));
				if (vi.gender == Gender.SHE)
					new NoTag(option, "[Nenhuma]");
				else
					new NoTag(option, "[Nenhum]");

				if (vi.fld.isAnnotationPresent(NotNull.class))
					select.addAttributes(new CustomAttribute("ng-required", "true"));
				HtmlTemplateBuilder.addAttr(vi.getAttr(), select, "__FIELD_NAME__", vi.name);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section, "__FIELD_NAME__", vi.name);
	}
}
