package com.crivano.juia.biz;

import com.crivano.jbiz.IActor;
import com.crivano.jbiz.IEntity;
import com.crivano.jbiz.IEvent;
import com.crivano.jlogic.Expression;
import com.crivano.juia.annotations.Global;

public interface IJuiaMiniAction<E extends IEntity, C extends IEvent<E>> {
	default String getName() {
		return this.getClass().getAnnotation(Global.class).action();
	}

	default String getIcon() {
		return this.getClass().getAnnotation(Global.class).icon();
	}

	default String getClick(String name) {
		return "this.actMiniQuery('" + name + "')";
	}

	default Expression getActive(IEntity entity, C event) {
		return null;
	}

	default public String getConfirmation(E entity, C event) {
		return null;
	}

	// @Override
	// public String getClick() {
	// return IMiniAction.Support.getClick(getName());
	// }

	void execute(IActor actor, IActor onBehalfOf, E entity, C event) throws Exception;
}
