package com.crivano.juia.html.control;

import com.crivano.jbiz.IEnum;
import com.crivano.juia.ViewItem;
import com.crivano.juia.html.HtmlTemplateBuilder;
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

public class SelectControl {
	public static void render(Div parent, ClassAttribute col, final ViewItem vi) {
		new Section(parent, col) {
			{
				new Label(this, new CustomAttribute("for", vi.fld.getName()),
						new Title(vi.hint), new ClassAttribute("label")) {
					{
						new NoTag(this, vi.caption);
					}
				};
				new Label(this, new CustomAttribute("for", vi.fld.getName()),
						new Title(vi.hint), new ClassAttribute("select")) {
					{

						Select select = new Select(this) {
							{

								if (IEnum.class.isAssignableFrom(vi.fld
										.getType())) {
									for (final IEnum v : (IEnum[]) vi.fld
											.getType().getEnumConstants()) {
										new Option(this, new Value(v.getId()
												.toString())) {
											{
												new NoTag(this, v.getDescr());
											}
										};
									}
								}
							}
						};
						HtmlTemplateBuilder.addAttr(vi, select);
						new I(this);
					}
				};
			}
		};
	}
}
