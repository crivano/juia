package com.crivano.juia.html;

import com.crivano.juia.AnnotationViewBuilder;
import com.crivano.juia.ControlContainer;
import com.crivano.juia.View;
import com.crivano.juia.View.Kind;
import com.crivano.juia.ViewDirector;
import com.crivano.juia.control.ButtonCancel;
import com.crivano.juia.control.ButtonDelete;
import com.crivano.juia.control.ButtonNew;
import com.crivano.juia.control.ButtonSave;
import com.crivano.juia.control.Control;
import com.crivano.juia.control.FieldCheck;
import com.crivano.juia.control.FieldCheckActive;
import com.crivano.juia.control.FieldCombo;
import com.crivano.juia.control.FieldComplete;
import com.crivano.juia.control.FieldDate;
import com.crivano.juia.control.FieldFile;
import com.crivano.juia.control.FieldHidden;
import com.crivano.juia.control.FieldInteger;
import com.crivano.juia.control.FieldMoney;
import com.crivano.juia.control.FieldMultipleSelect;
import com.crivano.juia.control.FieldNumeric;
import com.crivano.juia.control.FieldRefSelect;
import com.crivano.juia.control.FieldSelect;
import com.crivano.juia.control.FieldText;
import com.crivano.juia.control.FieldTime;
import com.crivano.juia.control.Repeat;
import com.crivano.juia.control.Sidebar;
import com.crivano.juia.control.TableColumn;
import com.crivano.juia.control.Topic;
import com.crivano.juia.html.control.CheckBoxControl;
import com.crivano.juia.html.control.CompleteBoxControl;
import com.crivano.juia.html.control.DateBoxControl;
import com.crivano.juia.html.control.FileControl;
import com.crivano.juia.html.control.HiddenControl;
import com.crivano.juia.html.control.IntegerControl;
import com.crivano.juia.html.control.MoneyBoxControl;
import com.crivano.juia.html.control.MultipleSelectControl;
import com.crivano.juia.html.control.NumericControl;
import com.crivano.juia.html.control.RefSelectControl;
import com.crivano.juia.html.control.SelectControl;
import com.crivano.juia.html.control.StringSelectControl;
import com.crivano.juia.html.control.TextBoxControl;
import com.crivano.juia.html.control.TimeBoxControl;
import com.crivano.juia.html.control.TitleControl;
import com.crivano.juia.html.control.TopicControl;
import com.webfirmframework.wffweb.tag.html.AbstractHtml;
import com.webfirmframework.wffweb.tag.html.H2;
import com.webfirmframework.wffweb.tag.html.attribute.Name;
import com.webfirmframework.wffweb.tag.html.attribute.Type;
import com.webfirmframework.wffweb.tag.html.attribute.core.AbstractAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Id;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.attribute.global.Title;
import com.webfirmframework.wffweb.tag.html.attributewff.CustomAttribute;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Button;
import com.webfirmframework.wffweb.tag.html.formsandinputs.FieldSet;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Form;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Input;
import com.webfirmframework.wffweb.tag.html.formsandinputs.Label;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Footer;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Header;
import com.webfirmframework.wffweb.tag.html.html5.stylesandsemantics.Section;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.tables.TBody;
import com.webfirmframework.wffweb.tag.html.tables.THead;
import com.webfirmframework.wffweb.tag.html.tables.Table;
import com.webfirmframework.wffweb.tag.html.tables.Td;
import com.webfirmframework.wffweb.tag.html.tables.Th;
import com.webfirmframework.wffweb.tag.html.tables.Tr;
import com.webfirmframework.wffweb.tag.htmlwff.CustomTag;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;

public class HtmlTemplateBuilder {

	private static String string;

	@SuppressWarnings("serial")
	static public Div build(final String prefix, Object o, View.Kind kind) {
		ViewDirector vd = new ViewDirector();
		vd.setViewBuilder(new AnnotationViewBuilder());
		vd.constructView(prefix, o, kind, false);
		final View view = vd.getView();

//		Div divBusy = new Div(null, new CustomAttribute("cg-busy",
//				"{promise:promise,message:'Por favor, aguarde...',templateUrl:'/resources/busy.html'}"));

		Div divWrapper = new Div(null, kind == View.Kind.SearchView ? new CustomAttribute("v-if", "list")
				: new CustomAttribute("v-if", "data"));

		// Breadcrumbs
		if ((kind == View.Kind.EditView && view.getSingular() != null)
				|| (kind == View.Kind.SearchView && view.getPlural() != null) || (kind == Kind.ShowView)) {
			TitleControl.render(divWrapper, view);
		}

//		Div divContainer = new Div(divWrapper, new ClassAttribute("container"));
		// Div divRow = new Div(divWrapper, new ClassAttribute("row"));

		Form form = null;
		FieldSet fieldSet = null;
		AbstractHtml footer = null;
		Div row = null;
		Div lastcol = null;
		Div divMainRow = null;

		if (kind == View.Kind.EditView) {
			form = new Form(divWrapper, new Id("form"), new Name(o.getClass().getSimpleName()),
					new ClassAttribute("juia-form"), new CustomAttribute("novalidate", "novalidate"),
					new CustomAttribute("v-if", "data"));
		} else if (kind == View.Kind.SearchView) {
			row = new Div(divWrapper, new ClassAttribute("row", "juia", "main-search-row"));
		} else if (kind == View.Kind.ShowView) {
			divMainRow = new Div(divWrapper, new ClassAttribute("row", "juia", "main-show-row"));
			Div col = new Div(divMainRow, new ClassAttribute("col", "col-md-8", "main-view"));
			row = new Div(col, new Id("content"), new ClassAttribute("row"));
		}

		for (int i = 0; i < view.getControls().size(); i++) {
			final Control vi = view.getControls().get(i);
			if (kind == View.Kind.EditView) {
				if (fieldSet == null || vi.newGroup != null || vi instanceof Repeat) {
					fieldSet = drawFieldset(form, vi);
				}
				if (row == null || vi.newRow || vi.newGroup != null || vi instanceof Repeat) {
					row = new Div(fieldSet, new ClassAttribute("row"));
				}
			} else if (kind == View.Kind.ShowView) {
				if (vi.newGroup != null)
					lastcol = drawGroupHeader(row, vi.newGroup, vi.attrGroup, vi.newRowGroup, vi.colXSGroup,
							vi.colSGroup, vi.colMGroup, vi.colLGroup, vi.colXLGroup);
			}
			if ((vi instanceof com.crivano.juia.control.Button
					|| vi instanceof com.crivano.juia.control.FieldCheckActive) && footer == null)
				if (form != null)
					footer = new Footer(form, new Id("footer"));
				else
					footer = new Div(row, new Id("footer"), new ClassAttribute("col col-sm-12"));
			if (vi instanceof com.crivano.juia.control.Sidebar) {
				row = drawSidebar(divMainRow);
			}

			i = renderControl(vi, i, prefix, view, fieldSet, row, lastcol, footer);
			if (vi instanceof Repeat) {
				row = null;
				fieldSet = null;
			}
		}
		return divWrapper;
	}

	private static FieldSet drawFieldset(AbstractHtml form, final Control vi) {
		FieldSet fieldSet;
		fieldSet = new FieldSet(form, new Title(vi.newGroup));
		addAttr(vi.attrGroup, fieldSet);
		if (vi.newGroup != null || vi instanceof Repeat) {
			String base = vi.name.substring(0, vi.name.lastIndexOf("."));
			fieldSet.addAttributes(new CustomAttribute("v-if", base));
			String s = vi.newGroup;
			if (s == null && vi instanceof Repeat)
				s = ((Repeat) vi).getPlural();
			final String caption = s;
			Header header = new Header(fieldSet,
					new ClassAttribute("form-group " + (vi.strongGroup ? "juia-strong" : "juia")));
			Div hr = new Div(header, new ClassAttribute("row align-items-center"));
			Div hc = new Div(hr, new ClassAttribute("col col-auto"));
			NoTag noTag = new NoTag(hc, caption);
		}
		return fieldSet;
	}

	static protected int renderControl(final Control control, int i, final String prefix,
			final ControlContainer container, FieldSet fieldSet, Div row, Div lastcol, AbstractHtml footer) {
		String col = null;
		if (control instanceof com.crivano.juia.control.Field) {
			com.crivano.juia.control.Field f = (com.crivano.juia.control.Field) control;
			col = "col" + (f.colXS != 0 ? " col-" + f.colXS : "") + (f.colS != 0 ? " col-sm-" + f.colS : "")
					+ (f.colM != 0 ? " col-md-" + f.colM : "") + (f.colL != 0 ? " col-lg-" + f.colL : "")
					+ (f.colXL != 0 ? " col-xl-" + f.colXL : "");
		}

		if (control instanceof Repeat) {
			final Repeat repeat = (Repeat) control;
			row.addAttributes(new CustomAttribute("v-for", "(" + repeat.fldName + "Item, $index)  in " + repeat.name),
					new CustomAttribute(":key", repeat.fldName + "Item.id"));

			Div hc = new Div(fieldSet.getChildren().get(0).getChildren().get(0),
					new ClassAttribute("col col-auto ml-auto"));

			Button btnPlus = new Button(hc, new ClassAttribute("juia btn btn-sm btn-secondary"),
					new Style("margin-left: 1em;"), new CustomAttribute("@click.prevent",
							repeat.name + " = (" + repeat.name + " || []);" + repeat.name + ".push({}); proxify();"));
			new NoTag(btnPlus, "&plus;&nbsp;" + repeat.getSingular());

			Div colToSidebar = new Div(row, new ClassAttribute("col col-auto"));
			Section section = new Section(colToSidebar);
			Label label = Utils.label(section, null);
			Label label2 = new Label(section, new ClassAttribute("input float-right"));

			CustomTag btnGrp = new CustomTag("b-dropdown", label2, new CustomAttribute(":text", "''+($index+1)"),
					new CustomAttribute("dropright"));

			CustomTag btnUp = new CustomTag("b-dropdown-item", btnGrp, // new CustomAttribute("text", "fa fa-arrow-up"),
					new CustomAttribute("@click.prevent", repeat.name + "[$index] = " + repeat.name
							+ ".splice($index - 1, 1, " + repeat.name + "[$index])[0]; proxifyAll()"),
					new CustomAttribute(":disabled", "$index === 0"));
			new NoTag(btnUp, "Subir");

			CustomTag btnDown = new CustomTag("b-dropdown-item", btnGrp,
					// new CustomAttribute("text", "fa fa-arrow-down"),
					new CustomAttribute("@click.prevent",
							repeat.name + "[$index] = " + repeat.name + ".splice($index + 1, 1, " + repeat.name
									+ "[$index])[0]; proxifyAll()"),
					new CustomAttribute(":disabled", "$index === " + repeat.name + ".length - 1"));
			new NoTag(btnDown, "Descer");

			CustomTag btnDel = new CustomTag("b-dropdown-item", btnGrp, // new CustomAttribute("text", "fa fa-trash"),
					new CustomAttribute("@click.prevent", repeat.name + ".splice($index, 1); proxifyAll()"));
			new NoTag(btnDel, "Excluir");

			Div colToControls = new Div(row, new ClassAttribute("col"));
			Div rowToControls = new Div(colToControls, new ClassAttribute("row"));

			for (int k = 0; k < repeat.getControls().size(); k++) {
				final Control vi = repeat.getControls().get(k);
				if (vi instanceof Repeat) {
					FieldSet colRepeat = drawFieldset(rowToControls, vi);
					colRepeat.addAttributes(new CustomAttribute("class", "col col-12"));
					Div rowRepeat = new Div(colRepeat, new ClassAttribute("row"));

					k = renderControl(vi, k, prefix, repeat, colRepeat, rowRepeat, lastcol, footer);
				} else
					k = renderControl(vi, k, prefix, repeat, fieldSet, rowToControls, lastcol, footer);
			}

		} else if (control instanceof Sidebar) {
			final Sidebar sidebar = (Sidebar) control;

			for (int k = 0; k < sidebar.getControls().size(); k++) {
				final Control vi = sidebar.getControls().get(k);
				if (vi.newGroup != null)
					lastcol = drawGroupHeader(row, vi.newGroup, vi.attrGroup);
				k = renderControl(vi, k, prefix, sidebar, fieldSet, row, lastcol, footer);
			}
		} else if (control instanceof FieldComplete) {
			CompleteBoxControl.render(row, col, (FieldComplete) control);
		} else if (control instanceof FieldRefSelect) {
			RefSelectControl.render(row, col, (FieldRefSelect) control);
		} else if (control instanceof FieldSelect) {
			StringSelectControl.render(row, col, (FieldSelect) control);
		} else if (control instanceof FieldFile) {
			FileControl.render(row, col, (FieldFile) control);
		} else if (control instanceof FieldText) {
			TextBoxControl.render(row, col, (FieldText) control);
		} else if (control instanceof FieldHidden) {
			HiddenControl.render(row, col, (FieldHidden) control);
		} else if (control instanceof FieldInteger) {
			IntegerControl.render(row, col, (FieldInteger) control);
		} else if (control instanceof FieldNumeric) {
			NumericControl.render(row, col, (FieldNumeric) control);
		} else if (control instanceof Topic) {
			TopicControl.render(lastcol, col, (Topic) control);
		} else if (control instanceof FieldCheck) {
			CheckBoxControl.render(row, col, (FieldCheck) control);
		} else if (control instanceof FieldCombo) {
			SelectControl.render(row, col, (FieldCombo) control);
		} else if (control instanceof FieldMultipleSelect) {
			MultipleSelectControl.render(row, col, (FieldMultipleSelect) control);
		} else if (control instanceof FieldDate) {
			DateBoxControl.render(row, col, (FieldDate) control);
		} else if (control instanceof FieldTime) {
			TimeBoxControl.render(row, col, (FieldTime) control);
		} else if (control instanceof FieldMoney) {
			MoneyBoxControl.render(row, col, (FieldMoney) control);
		} else if (control instanceof TableColumn) {
			TableColumn tableColumn = (TableColumn) control;
			Div columnPag = new Div(row, new ClassAttribute("col col-sm-12"));
			new CustomTag("dir-pagination-controls", columnPag, new CustomAttribute("max-size", "9"),
					new CustomAttribute("direction-links", "true"), new CustomAttribute("boundary-links", "true"));
			Div column = new Div(row, new ClassAttribute("col col-sm-12"));
			Table table = new Table(column, new ClassAttribute("table table-striped table-sm table-hover"));
			THead thead = new THead(table);
			Tr trh = new Tr(thead);
			for (int j = i; j < container.getControls().size(); j++) {
				final Control vitc = container.getControls().get(j);
				if (!(vitc instanceof TableColumn))
					break;
				Th th = new Th(trh);
				new NoTag(th, vitc.caption);
			}
			TBody tbody = new TBody(table);
			Tr trb = new Tr(tbody,
					new CustomAttribute("dir-paginate", "data in list | filter: filter | itemsPerPage:20"),
					tableColumn.skipShow ? new CustomAttribute("@click.prevent", "click(data)")
							: new CustomAttribute("@click.prevent", "click(data)"));
			for (int j = i; j < container.getControls().size(); j++) {
				final Control vitc = container.getControls().get(j);
				if (!(vitc instanceof TableColumn))
					break;
				i = j;
				Td td = new Td(trb);
				new NoTag(td, "{{" + prefix + ((TableColumn) vitc).fieldName + "}}");
			}

			return i;
		} else if (control instanceof ButtonDelete) {
			new Button(footer, new ClassAttribute("btn btn-danger no-print"),
					new CustomAttribute("@click.prevent", "remove()"), new CustomAttribute("v-if", "data.id||false")) {
				{
					new NoTag(this, "Excluir");
				}
			};
		} else if (control instanceof FieldCheckActive) {
			Div div = new Div(footer, new ClassAttribute("btn btn-light"));
			Div section = new Div(div, new ClassAttribute("xform-check xform-group"),
					new Style("margin-left: 1.25rem"));
			Input input = new Input(section, new Type("checkbox"), new ClassAttribute("form-check-input"),
					new CustomAttribute("v-model", control.name));
			Label lbl = new Label(input, new ClassAttribute("form-check-label"));
			new NoTag(lbl, control.caption);
		} else if (control instanceof ButtonNew) {
			new Button(footer, new ClassAttribute("btn btn-primary float-right no-print"),
					new Style("margin-left: 1em;"), new CustomAttribute("@click.prevent", "create()")) {
				{
					new NoTag(this, "Criar");// + container.getSingular());
				}
			};
		} else if (control instanceof ButtonSave) {
			new Button(footer, new ClassAttribute("btn btn-primary float-right no-print"),
					new Style("margin-left: 1em;"), new CustomAttribute("@click.prevent", "save()"),
					new CustomAttribute("formnovalidate")) {
				{
					new NoTag(this, "Salvar");
				}
			};
		} else if (control instanceof ButtonCancel) {
			new Button(footer, new ClassAttribute("btn btn-light float-right no-print"), new Style("margin-left: 1em;"),
					new CustomAttribute("@click.prevent", "cancel()")) {
				{
					new NoTag(this, "Cancelar");
				}
			};
		}
		return i;
	}

	public static void addAttr(String[] attrs, AbstractHtml el, String find, String replace) {
		if (attrs == null)
			return;
		for (String s : attrs) {
			if (s.length() == 0)
				continue;
			AbstractAttribute attr;
			int split = s.indexOf("=");
			if (split == -1)
				attr = new CustomAttribute(s);
			else
				attr = new CustomAttribute(s.substring(0, split),
						(find != null ? s.substring(split + 1).replace(find, replace) : s.substring(split + 1)));
			el.addAttributes(attr);
		}
	}

	public static AbstractAttribute getAttr(String attrName, String[] attrs, String find, String replace) {
		if (attrs == null)
			return null;
		for (String s : attrs) {
			if (s.length() == 0)
				continue;
			AbstractAttribute attr;
			int split = s.indexOf("=");
			if (split == -1)
				attr = new CustomAttribute(s);
			else
				attr = new CustomAttribute(s.substring(0, split),
						(find != null ? s.substring(split + 1).replace(find, replace) : s.substring(split + 1)));
			if (attrName.equals(attr.getAttributeName()))
				return attr;
		}
		return null;
	}

	public static void addAttr(String[] attrs, AbstractHtml el) {
		addAttr(attrs, el, null, null);
	}

	protected static Div drawSidebar(Div divRow) {
		Div row;
		Div col = new Div(divRow, new ClassAttribute("col", "col-md-4", "sidebar"));
		return new Div(col, new Id("sidebar"), new ClassAttribute("row"));
	}

	public static Div drawGroupHeader(Div row, String caption, String[] attr) {
		return drawGroupHeader(row, caption, attr, false, 12, 0, 0, 0, 0);
	}

	public static Div drawGroupHeader(Div row, String caption, String[] attr, boolean newRow, int colXS, int colS,
			int colM, int colL, int colXL) {
		if (caption == null)
			return null;
		ClassAttribute attrcol = new ClassAttribute("col" + (colXS != 0 ? " col-" + colXS : "")
				+ (colS != 0 ? " col-sm-" + colS : "") + (colM != 0 ? " col-md-" + colM : "")
				+ (colL != 0 ? " col-lg-" + colL : "") + (colXL != 0 ? " col-xl-" + colXL : ""));
		Div col = new Div(row, attrcol);
		Div headerDiv = new Div(col, new ClassAttribute("headline", "headline-md"));
		H2 header = new H2(headerDiv);
		NoTag noTag = new NoTag(header, caption);
		addAttr(attr, col);

		return col;
	}
}
