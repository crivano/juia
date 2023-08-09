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

	public enum AggregateInJsonArray {
		DEFAULT, YYYY_MM_DD
	}

	String name() default "";

	Align align() default Align.DEFAULT;

	Format format() default Format.DEFAULT;

	AggregateInJsonArray aggregateInJsonArray() default AggregateInJsonArray.DEFAULT;
}