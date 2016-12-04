package com.crivano.juia.html.control;

import com.crivano.juia.View;
import com.webfirmframework.wffweb.tag.html.H1;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Form;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class BreadcrumbControl {
	public static void render(final Div parent, final View view) {
		new Div(parent, new ClassAttribute("breadcrumbs")) {
			{
				new Div(this, new ClassAttribute("container")) {
					{
						new Div(this, new ClassAttribute("row")) {
							{
								new H1(
										this,
										new ClassAttribute(
												"col col-xs-12 col-sm-6 col-md-9 col-lg-10")) {
									{
										if (view.getKind() == View.Kind.EditView)
											new NoTag(this, "Edição de "
													+ view.getSingular());
										else
											new NoTag(this, "Lista de "
													+ view.getPlural());
									}
								};
								if (view.getKind() == View.Kind.SearchView) {
									new Form(
											this,
											new Name("Pessoa"),
											new ClassAttribute(
													"sky-form col col-xs-12 col-sm-6 col-md-3 col-lg-2"),
											new Style("border: none")) {
										{
											new Label(
													this,
													new Title(),
													new CustomAttribute("for",
															"code"),
													new ClassAttribute("label"),
													new Style(
															"padding-top: 0px;")) {
												{
													new NoTag(this, "Filtrar");
												}
											};
											new Label(
													this,
													new Title(),
													new CustomAttribute("for",
															"filtro"),
													new ClassAttribute("input"),
													new Style(
															"margin-bottom: 0px;")) {
												{
													new Input(
															this,
															new Id("filter"),
															new Name("filter"),
															new CustomAttribute(
																	"ng-model",
																	"filter"),
															new ClassAttribute(
																	"form-control"),
															new Type("text"));
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
		};
	}
}
