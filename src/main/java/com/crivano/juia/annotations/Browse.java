package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Browse {
	String caption() default "";

	String hint() default "";

	int zorder() default -1;
}
