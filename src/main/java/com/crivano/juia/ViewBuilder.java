package com.crivano.juia;

public abstract class ViewBuilder {
	protected View view;

	public View getView() {
		return view;
	}

	public void createNewView() {
		view = new View();
	}

	public abstract void buildView(String prefix, Object o, View.Kind kind,
			boolean frontPage);

	public void addSearchItems(boolean frontPage) {
		ViewItem vi = new ViewItem();
		vi.type = ViewItem.Type.SearchButton;
		vi.caption = "Pesquisar";
		// vi.gapLeftPush = true;
		vi.wrap = true;
		view.getViewGlueList().add(vi);

		vi = new ViewItem();
		vi.type = ViewItem.Type.Table;
		// vi.spanAll = true;
		// vi.percentWidth = 100;
		// vi.percentHeight = 100;
		vi.wrap = true;
		view.getViewGlueList().add(vi);

		if (!frontPage) {
			vi = new ViewItem();
			vi.type = ViewItem.Type.BackButton;
			vi.caption = "Voltar";
			// vi.spanAll = true;
			// vi.splitAll = true;
			// vi.gapRightPush = true;
			view.getViewGlueList().add(vi);
		}

		vi = new ViewItem();
		vi.type = ViewItem.Type.NewButton;
		vi.caption = "Novo";
		// if (frontPage) {
		// vi.spanAll = true;
		// vi.splitAll = true;
		// vi.gapLeftPush = true;
		// }
		view.getViewGlueList().add(vi);

		vi = new ViewItem();
		vi.type = ViewItem.Type.EditButton;
		vi.caption = "Editar";
		view.getViewGlueList().add(vi);

		vi = new ViewItem();
		vi.type = ViewItem.Type.DeleteButton;
		vi.caption = "Excluir";
		view.getViewGlueList().add(vi);
	}

	public void addEditItems(boolean frontPage) {
		ViewItem vi;

		vi = view.getViewGlueList().get(view.getViewGlueList().size() - 1);
		boolean fAfterATable = vi.type == ViewItem.Type.DeleteButton;
		if (fAfterATable)
			vi.wrap = true;

		if (!frontPage) {
			vi = new ViewItem();
			vi.type = ViewItem.Type.BackButton;
			vi.caption = "Voltar";
			view.getViewGlueList().add(vi);
		}

		vi = new ViewItem();
		vi.type = ViewItem.Type.DeleteButton;
		vi.caption = "Excluir";
		view.getViewGlueList().add(vi);

		vi = new ViewItem();
		vi.type = ViewItem.Type.CancelButton;
		vi.caption = "Cancelar";
		view.getViewGlueList().add(vi);

		vi = new ViewItem();
		vi.type = ViewItem.Type.SaveButton;
		vi.caption = "Gravar";
		view.getViewGlueList().add(vi);
	}

	public void addTableButtons(ViewItem viRelated, boolean fSelect) {
		ViewItem vi;

		// if (fSelect) {
		// vi = new ViewItem();
		// vi.viRelated = viRelated;
		// vi.type = ViewItem.Type.SelectButton;
		// vi.caption = "Selecionar";
		// vi.spanAll = true;
		// vi.splitAll = true;
		// vi.alignRight = true;
		// vi.newLine = true;
		// view.getViewGlueList().add(vi);
		// } else {
		vi = new ViewItem();
		vi.viRelated = viRelated;
		vi.type = ViewItem.Type.NewButton;
		vi.caption = "Novo";
		// vi.spanAll = true;
		// vi.splitAll = true;
		// vi.alignRight = true;
		// vi.newLine = true;
		view.getViewGlueList().add(vi);

	}
}
