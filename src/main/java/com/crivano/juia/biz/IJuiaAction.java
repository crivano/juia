package com.crivano.juia.biz;

import java.util.Date;

import com.crivano.jbiz.IAction;
import com.crivano.jbiz.IActor;
import com.crivano.jbiz.IEntity;
import com.crivano.jlogic.Expression;
import com.crivano.juia.View.Kind;
import com.crivano.juia.annotations.Global;
import com.crivano.juia.html.HtmlTemplateBuilder;
import com.crivano.juia.util.JuiaUtils;
import com.webfirmframework.wffweb.tag.html.AbstractHtml;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Form;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Footer;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;

public interface IJuiaAction<E extends IEntity, A extends IActor> {
	default String getName() {
		return this.getClass().getAnnotation(Global.class).action();
	}

	default String getIcon() {
		return this.getClass().getAnnotation(Global.class).icon();
	}

	default AbstractHtml getHtml() {
		Div div = HtmlTemplateBuilder.build("act.", this, Kind.EditView);
		Form frm = (Form) JuiaUtils.findChildByAttribute(div, new Id("form"));
		Footer footer = (Footer) JuiaUtils.findChildByAttribute(div, new Id("footer"));
		if (footer != null)
			frm.removeChild(footer);
		if (frm.getChildren().size() == 1 && frm.getChildren().get(0).getChildren().size() == 1
				&& frm.getChildren().get(0).getChildren().get(0).getChildren().size() == 0)
			return null;
		frm.addAttributes(new ClassAttribute("juia-form", "dialog-form"));
		frm.addAttributes(new CustomAttribute("ng-submit", "close()"));
		return frm;

	}

	default String getConfirmation(E element) {
		return null;
	}

	default Expression getActive(E element) {
		return null;
	}

	default String getClick() {
		return "this.actQuery('" + this.getName() + "')";
	}

	default void init(E element) {

	};

	default void beforeValidate(E element) {
	};

//	default String getNext() {
//	};

	default void execute(A actor, A onBehalfOf, E element) throws Exception {
	}

	default String getNext() {
		return null;
	};
}