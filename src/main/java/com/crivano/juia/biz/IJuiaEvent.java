package com.crivano.juia.biz;

import com.crivano.jbiz.IActor;
import com.crivano.jbiz.IEntity;
import com.crivano.jbiz.IEvent;
import com.crivano.jbiz.ITag;
import com.crivano.jlogic.Expression;

public interface IJuiaEvent<E extends IEntity, A extends IActor, V extends IEvent<E>, C extends IJuiaAction<E, A, V, T>, T extends ITag>
		extends IEvent<E> {

	Expression getActiveMiniAction(A actor, A onBehalfOf, E element, C miniAction);

	// Expression getActiveMiniAction(A actor, A onBehalfOf, E element,
	// IJuiaMiniAction<E, ?> miniAction);
}
