package com.crivano.juia.html;

import com.crivano.juia.AnnotationViewBuilder;
import com.crivano.juia.View;
import com.crivano.juia.ViewDirector;
import com.crivano.juia.ViewItem;
import com.crivano.juia.html.control.BreadcrumbControl;
import com.crivano.juia.html.control.CheckBoxControl;
import com.crivano.juia.html.control.CompleteBoxControl;
import com.crivano.juia.html.control.DateBoxControl;
import com.crivano.juia.html.control.MoneyBoxControl;
import com.crivano.juia.html.control.SelectControl;
import com.crivano.juia.html.control.TextBoxControl;
import com.webfirmframework.wffweb.tag.html.AbstractHtml;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.core.AbstractAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Button;
import com.webfirmframework.wffweb.tag.html.formsandinputs.FieldSet;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Form;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Footer;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Header;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.tables.TBody;
import com.webfirmframework.wffweb.tag.html.tables.THead;
import com.webfirmframework.wffweb.tag.html.tables.Table;
import com.webfirmframework.wffweb.tag.html.tables.Td;
import com.webfirmframework.wffweb.tag.html.tables.Th;
import com.webfirmframework.wffweb.tag.html.tables.Tr;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class HtmlTemplateBuilder {

	@SuppressWarnings("serial")
	static public Div build(final String prefix, Object o, View.Kind kind) {
		ViewDirector vd = new ViewDirector();
		vd.setViewBuilder(new AnnotationViewBuilder());
		vd.constructView(prefix, o, kind, false);
		final View view = vd.getView();

		Div divBusy = new Div(
				null,
				new CustomAttribute(
						"cg-busy",
						"{promise:promise,message:'Por favor, aguarde...',templateUrl:'/resources/busy.html'}"));
		Div divWrapper = new Div(divBusy, new ClassAttribute("wrapper"));

		// Breadcrumbs
		if ((kind == View.Kind.EditView && view.getSingular() != null)
				|| (kind == View.Kind.SearchView && view.getPlural() != null)) {
			BreadcrumbControl.render(divWrapper, view);
		}

		Div divContainer = new Div(divWrapper, new ClassAttribute(
				"container content profile"));
		Div divRow = new Div(divContainer, new ClassAttribute("row"));

		Form form = new Form(divRow, new Name(o.getClass().getSimpleName()),
				new ClassAttribute("sky-form")) {
			{
				FieldSet fieldSet = null;
				Footer footer = null;
				Div row = null;
				boolean first = true;

				for (int i = 0; i < view.getViewGlueList().size(); i++) {
					final ViewItem vi = view.getViewGlueList().get(i);
					if (first || vi.newFieldSet != null) {
						fieldSet = new FieldSet(this, new Title(vi.newFieldSet));
						if (vi.newFieldSet != null)
							new Header(
									fieldSet,
									new Style(
											"margin-top: -25px; margin-left: -30px; margin-right: -30px; margin-bottom: 6px;")) {
								{
									new NoTag(this, vi.newFieldSet);
								}
							};
					}
					if (first || vi.newRow || vi.newFieldSet != null)
						row = new Div(fieldSet, new ClassAttribute("row"));
					first = false;

					ClassAttribute col = new ClassAttribute("col"
							+ (vi.colXS != 0 ? " col-xs-" + vi.colXS : "")
							+ (vi.colS != 0 ? " col-sm-" + vi.colS : "")
							+ (vi.colM != 0 ? " col-md-" + vi.colM : "")
							+ (vi.colL != 0 ? " col-lg-" + vi.colL : "")
							+ (vi.colXL != 0 ? " col-xl-" + vi.colXL : ""));

					switch (vi.type) {
					case SearchButton:
					case BackButton:
					case DeleteButton:
					case EditButton:
					case NewButton:
					case SaveButton:
						if (footer == null)
							footer = new Footer(this);
						break;
					}

					switch (vi.type) {
					case CompleteBox:
						CompleteBoxControl.render(row, col, vi);
						break;

					case TextBox:
						TextBoxControl.render(row, col, vi);
						break;

					case CheckBox:
						CheckBoxControl.render(row, col, vi);
						break;

					case ComboBox:
						SelectControl.render(row, col, vi);
						break;

					case DateBox:
						DateBoxControl.render(row, col, vi);
						break;

					case MoneyBox:
						MoneyBoxControl.render(row, col, vi);
						break;

					case TableColumn: {
						Table table = new Table(this, new ClassAttribute(
								"table table-striped"));
						THead thead = new THead(table);
						Tr trh = new Tr(thead);
						for (int j = i; j < view.getViewGlueList().size(); j++) {
							final ViewItem vitc = view.getViewGlueList().get(j);
							if (vitc.type != ViewItem.Type.TableColumn)
								break;
							Th th = new Th(trh);
							new NoTag(th, vitc.caption);
						}
						TBody tbody = new TBody(table);
						Tr trb = new Tr(
								tbody,
								new CustomAttribute("dir-paginate",
										"data in list | filter: filter | itemsPerPage:5"),
								new CustomAttribute("ng-click",
										"edit(data.key)"));
						for (int j = i; j < view.getViewGlueList().size(); j++) {
							final ViewItem vitc = view.getViewGlueList().get(j);
							if (vitc.type != ViewItem.Type.TableColumn)
								break;
							i = j;
							Td td = new Td(trb);
							new NoTag(td, "{{" + prefix + vitc.fld.getName()
									+ "}}");
						}
						break;
					}
					case SearchButton:
						break;
					case BackButton:
						break;
					case DeleteButton:
						new Button(
								footer,
								new ClassAttribute("btn-u btn-u-red"),
								new CustomAttribute("ng-click", "remove()"),
								new CustomAttribute("ng-show", "data.id||false")) {
							{
								new NoTag(this, "Excluir");
							}
						};
						break;
					case EditButton:
						break;
					case NewButton:
						new Button(footer, new ClassAttribute(
								"btn-u btn-u-blue pull-right"), new Style(
								"margin-left: 1em;"), new CustomAttribute(
								"ng-click", "create()")) {
							{
								new NoTag(this, "Criar " + view.getSingular());
							}
						};
						break;
					case SaveButton:
						new Button(footer, new ClassAttribute(
								"btn-u btn-u-blue pull-right"), new Style(
								"margin-left: 1em;"), new CustomAttribute(
								"ng-click", "save()")) {
							{
								new NoTag(this, "Salvar");
							}
						};
						break;
					case CancelButton:
						new Button(footer, new ClassAttribute(
								"btn-u btn-u-default pull-right"), new Style(
								"margin-left: 1em;"), new CustomAttribute(
								"ng-click", "cancel()")) {
							{
								new NoTag(this, "Cancelar");
							}
						};
						break;
					}
				}
			}

		};
		return divBusy;
	}

	public static void addAttr(final ViewItem vi, AbstractHtml el) {
		for (String s : vi.attr) {
			if (s.length() == 0)
				continue;
			AbstractAttribute attr;
			int split = s.indexOf("=");
			if (split == -1)
				attr = new CustomAttribute(s);
			else
				attr = new CustomAttribute(s.substring(0, split),
						s.substring(split + 1));
			el.addAttributes(attr);
		}
	}
}
