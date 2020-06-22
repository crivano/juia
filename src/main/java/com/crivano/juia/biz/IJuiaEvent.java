package com.crivano.juia.biz;

import com.crivano.jbiz.IActor;
import com.crivano.jbiz.IEntity;
import com.crivano.jbiz.IEvent;
import com.crivano.jlogic.Expression;

public interface IJuiaEvent<E extends IEntity, A extends IActor> extends IEvent<E> {

	Expression getActiveMiniAction(A actor, A onBehalfOf, E element, IJuiaMiniAction miniAction);

	// Expression getActiveMiniAction(A actor, A onBehalfOf, E element, IJuiaMiniAction<E, ?> miniAction);
}
