package com.crivano.juia.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.crivano.juia.View;
import com.crivano.juia.View.Kind;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Menu {
	String caption() default "";

	String hint() default "";

	View.Kind kind();
}
