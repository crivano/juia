package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.crivano.juia.ViewItem;
import com.crivano.juia.ViewItem.Width;

@Retention(RetentionPolicy.RUNTIME)
public @interface Edit {
	String caption() default "";

	String hint() default "";

	int size() default -1;

	Width width() default Width.Normal;

	int zorder() default -1;

	boolean wrap() default false;

	boolean spanAll() default false;

	String mappedBy() default "";

	int colXS() default 12;

	int colS() default 0;

	int colM() default 0;

	int colL() default 0;

	int colXL() default 0;

	boolean newRow() default false;

	String[] attr() default "";
}
