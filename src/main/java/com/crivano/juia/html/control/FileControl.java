package com.crivano.juia.html.control;

import com.crivano.juia.control.FieldFile;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.html.Utils;
import com.webfirmframework.wffweb.tag.html.attribute.Role;
import com.webfirmframework.wffweb.tag.html.attribute.Src;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Button;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.images.Img;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Span;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class FileControl {

	public static void render(Div parent, ClassAttribute col, final FieldFile vi) {
		Section section = new Section(parent, new ClassAttribute(col.getAttributeValue() + " form-group")) {
			{
				Utils.label(this, vi);
				Div divUpload = new Div(this, new ClassAttribute("row")) {
					{
						new Section(this, new ClassAttribute("col col-sm-auto align-self-center"),
								new CustomAttribute("ng-show",
										vi.name + " && (" + vi.name + ".originalObject.description == 'image/jpeg' || "
												+ vi.name + ".originalObject.description == 'image/png')")) {
							{
								new Div(this, new Id("thumbnail"), new ClassAttribute("kjh")) {
									{
										new Img(this, new Style("max-width: 10em; max-height: 10em;"), new Src(
												"/app/core-arquivo/download/{{" + vi.name + ".originalObject.key}}")) {
										};
									}
								};
							}
						};
						new Section(this, new ClassAttribute("col col-sm-auto align-self-center")) {
							{
								Button btn = new Button(this, new ClassAttribute("btn btn-info"),
										new Style("width: 100%"), new Type("file"), new CustomAttribute("ngf-select",
												"uploadFiles('" + vi.name + "', $files, $invalidFiles)")
								// new Accept("application/pdf"),
								// new CustomAttribute("ngf-pattern",
								// "application/pdf"),
								// new CustomAttribute("ngf-max-size",
								// "32MB")
								) {
									{
										new NoTag(this, "Selecione o Arquivo");
									}
								};
								HtmlTemplateBuilder.addAttr(vi.getAttr(), btn);
							}
						};
						new Section(this, new ClassAttribute("col align-self-center")) {
							{
								new Div(this, new ClassAttribute("progress"), new Style("width: 100%;"),
										new CustomAttribute("ng-show", vi.name + "_uploadProgress >= 0")) {
									{
										new Div(this, new Id("progressbar-ad"),
												new ClassAttribute("progress-bar progress-bar-info"),
												new Role("progressbar"),
												new CustomAttribute("aria-valuenow", "{{progress}}"),
												new CustomAttribute("aria-valuemin", "0"),
												new CustomAttribute("aria-valuemax", "100"), new CustomAttribute(
														"style", "width : {{" + vi.name + "_uploadProgress}}%")) {
											{
												new Span(this) {
													{
														new NoTag(this, " {{" + vi.name + "_uploadMessage}}");
													}
												};
											}
										};
									}
								};
							}
						};
						// new Br(this);
						// new Br(this);
						// new NoTag(this, " File: ");
						// new Div(this, new Style("font:smaller")) {
						// {
						// new NoTag(this,
						// "\n{{f.name}} {{errFile.name}} {{errFile.$error}} {{errFile.$errorParam}} ");
						// new Span(this, new ClassAttribute("progress"),
						// new CustomAttribute("ng-show",
						// "progress >= 0")) {
						// {
						// }
						// };
						// }
						// };
						// new NoTag(this, " {{errorMsg}} ");
					}
				};

			}
		};
		HtmlTemplateBuilder.addAttr(vi.attrContainer, section, "__FIELD_NAME__", vi.name);
	}
}
