package com.crivano.juia.html.control;

import com.crivano.juia.ViewItem;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class MoneyBoxControl {

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
						new Title(vi.hint), new ClassAttribute("input")) {
					{
						new I(this, new ClassAttribute(
								"icon-append fa fa-dollar"));
						Input input = new Input(this, new Type("text"),
								new ClassAttribute("form-control"), new Id(
										vi.fld.getName()), new CustomAttribute(
										"ng-model", vi.name),
								new CustomAttribute("ui-money-mask", "2"),
								new Name(vi.fld.getName()), new ClassAttribute(
										"isMoney"));
						HtmlTemplateBuilder.addAttr(vi, input);
					}
				};
			}
		};
	}
}
