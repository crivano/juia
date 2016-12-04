package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.crivano.juia.ViewItem;
import com.crivano.juia.ViewItem.Width;

@Retention(RetentionPolicy.RUNTIME)
public @interface Search {
	String caption() default "";

	String hint() default "";
	
	int size() default -1;

	Width width() default Width.Normal;

	int zorder() default -1;

	boolean wrap() default false;
}
