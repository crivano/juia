package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Show {
	String caption() default "";

	String value() default "";

	String hint() default "";

	String[] attr() default "";

	int colXS() default 12;

	int colS() default 0;

	int colM() default 0;

	int colL() default 0;

	int colXL() default 0;
}
