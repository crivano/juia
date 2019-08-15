package com.crivano.juia;

import com.crivano.juia.control.ButtonCancel;
import com.crivano.juia.control.ButtonDelete;
import com.crivano.juia.control.ButtonNew;
import com.crivano.juia.control.ButtonSave;
import com.crivano.juia.control.FieldCheckActive;

public abstract class ViewBuilder {
	protected View view;

	public View getView() {
		return view;
	}

	public void createNewView() {
		view = new View();
	}

	public abstract void buildView(String prefix, Object o, View.Kind kind, boolean frontPage);

	public void addSearchItems(boolean frontPage) {
		view.getControls().add(new ButtonNew("Novo"));
	}

	public void addShowItems(boolean frontPage) {
	}

	public void addEditItems(boolean deletable, boolean inactivable, boolean frontPage) {
		if (deletable)
			view.getControls().add(new ButtonDelete("Excluir"));
		if (inactivable)
			view.getControls().add(new FieldCheckActive("Ativo"));
		view.getControls().add(new ButtonCancel("Cancelar"));
		view.getControls().add(new ButtonSave("Gravar"));
	}

	public void addTableButtons() {
		view.getControls().add(new ButtonNew("Novo"));
	}
}
