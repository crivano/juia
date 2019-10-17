package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldSet {
	String caption() default "<none>";

	String[] attr() default "";

	boolean strong() default false;
}
