package com.crivano.juia.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Menu {
	String caption() default "";

	String hint() default "";

	boolean create() default false;

	boolean list() default false;
}
