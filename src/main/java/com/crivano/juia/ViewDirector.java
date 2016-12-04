package com.crivano.juia;

import com.crivano.juia.View.Kind;

public class ViewDirector {
	private ViewBuilder viewBuilder;

	public void setViewBuilder(ViewBuilder pb) {
		viewBuilder = pb;
	}

	public View getView() {
		return viewBuilder.getView();
	}

	public void constructView(String prefix, Object o, Kind kind,
			boolean fFrontPage) {
		viewBuilder.createNewView();
		viewBuilder.buildView(prefix, o, kind, fFrontPage);
	}
}
