package com.crivano.juia.html.control;

import com.crivano.juia.View;
import com.crivano.juia.annotations.Global.Gender;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.FieldSet;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Form;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class TitleControl {
	public static void render(final Div parent, final View view) {
		new FieldSet(parent, new ClassAttribute("juia juia-title")) {
			{
				new Div(this, new ClassAttribute("row align-items-center")) {
					{
						new Div(this, new ClassAttribute("col col-auto")) {
							{
								new CustomTag("big", this, new ClassAttribute("juia")) {
									{
										if (view.getKind() == View.Kind.EditView)
											new NoTag(this, "Edição de " + view.getSingular());
										else if (view.getKind() == View.Kind.SearchView)
											new NoTag(this,
													"Lista de " + view.getPlural()
															+ (view.getInactivable() ? " Ativ"
																	+ (view.getGender() == Gender.SHE ? "a" : "o") + "s"
																	: ""));
										else if (view.getKind() == View.Kind.ShowView) {
											if (view.getSingular() != null)
												new NoTag(this, "{{data.title}}");
										}
									}
								};
							}
						};
						if (view.getKind() == View.Kind.SearchView) {
							new Div(this, new ClassAttribute("col col-auto ml-auto no-print")) {
								{
									new Form(this, new Name("filtrar"), new ClassAttribute("juia-form"),
											new Style("border: none;")) {
										{
											new Label(this, new Title(), new CustomAttribute("for", "code"),
													new ClassAttribute("label"), new Style("padding-top: 0px;")) {
												{
													new NoTag(this, "Filtrar");
												}
											};
											new Input(this, new Id("filter"), new Name("filter"),
													new CustomAttribute("ng-model", "filter"),
													new ClassAttribute("form-control"), new Type("text"));

										}
									};
								}
							};
						}
					}
				};
			}
		};
	}
}
