package com.crivano.juia.html;

import javax.validation.constraints.NotNull;

import com.crivano.juia.annotations.Edit;
import com.crivano.juia.control.Field;
import com.webfirmframework.wffweb.tag.html.AbstractHtml;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formatting.I;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class Utils {
	public static Label label(final AbstractHtml parent, final Field vi) {
		if (vi != null)
			return new Label(parent, new CustomAttribute("for", vi.fld.getName()), new Title(vi.hint),
					new ClassAttribute("label")) {
				{
					new NoTag(this, vi.caption);
					labelClue(this, vi.fld);
				}
			};
		else
			return new Label(parent, new Title(""), new ClassAttribute("label")) {
				{
					new NoTag(this, "&nbsp;");
				}
			};

	}

	public static void labelClue(final Label label, final java.lang.reflect.Field fld) {
		if (fld.isAnnotationPresent(NotNull.class))
			new I(label, new Title("Preenchimento obrigatório"), new ClassAttribute("label-clue fa fa-asterisk"));

		if (fld.isAnnotationPresent(Edit.class)) {
			Edit edit = fld.getAnnotation(Edit.class);
			if (edit.hint() != null && edit.hint().length() > 0)
				new I(label, new ClassAttribute("label-clue fa fa-question"));
		}
	}

}
