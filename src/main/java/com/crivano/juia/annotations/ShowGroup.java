package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ShowGroup {
	String caption() default "<none>";

	int colXS() default 12;

	int colS() default 0;

	int colM() default 0;

	int colL() default 0;

	int colXL() default 0;

	boolean newRow() default false;
}
