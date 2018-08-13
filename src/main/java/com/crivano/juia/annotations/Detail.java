package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Detail {
	String caption() default "";

	String hint() default "";

	String[] attr() default "";
}
