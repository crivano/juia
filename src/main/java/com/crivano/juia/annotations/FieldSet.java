package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.crivano.juia.ViewItem;
import com.crivano.juia.ViewItem.Width;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldSet {
	String caption() default "<none>";

	String show() default "";
}
