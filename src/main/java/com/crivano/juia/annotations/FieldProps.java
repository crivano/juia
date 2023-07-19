package com.crivano.juia.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FieldProps {
	public enum Align {
		DEFAULT, LEFT, RIGHT, CENTER
	}

	public enum Format {
		DEFAULT, DATE, DATE_HH_MM_SS
	}

	String name() default "";

	Align align() default Align.DEFAULT;

	Format format() default Format.DEFAULT;
}