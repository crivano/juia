package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Search {
	String caption() default "";

	String hint() default "";

	int size() default -1;

	int zorder() default -1;

	boolean wrap() default false;
}
