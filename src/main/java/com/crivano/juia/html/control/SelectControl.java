package com.crivano.juia.html.control;

import javax.validation.constraints.NotNull;

import com.crivano.jbiz.IEnum;
import com.crivano.juia.control.FieldCombo;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Value;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Option;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Select;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class SelectControl {
	public static void render(Div parent, ClassAttribute col, final FieldCombo vi) {
		Section section = new Section(parent, new ClassAttribute(col.getAttributeValue() + " form-group")) {
			{
				Select select = new Select(Utils.label(this, vi), new CustomAttribute("ng-model", vi.name),
						new ClassAttribute("form-control")) {
					{

						if (IEnum.class.isAssignableFrom(vi.fld.getType())) {
							for (final IEnum v : (IEnum[]) vi.fld.getType().getEnumConstants()) {
								Option option = new Option(this, new Value(v.getCode())) {
									{
										new NoTag(this, v.getDescr());
									}
								};
								HtmlTemplateBuilder.addAttr(vi.attrItem, option, "__VALUE__", v.getCode());
							}
						}
					}
				};
				if (vi.fld.isAnnotationPresent(NotNull.class))
					select.addAttributes(new CustomAttribute("ng-required", "true"));
				HtmlTemplateBuilder.addAttr(vi.attr, select);
				new I(this);
			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section);
	}
}
