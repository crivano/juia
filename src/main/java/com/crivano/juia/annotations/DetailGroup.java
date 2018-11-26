package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public abstract @interface DetailGroup {
	String caption() default "<none>";

	String show() default "";
}
