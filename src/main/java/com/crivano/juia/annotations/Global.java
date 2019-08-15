package com.crivano.juia.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Global {
	public enum Gender {
		HE, SHE, IT
	}

	String singular();

	String plural();

	String action() default "";

	String icon() default "";

	Gender gender() default Gender.IT;

	String locator() default "";

	String codePrefix() default "";

	boolean deletable() default false;

	boolean inactivable() default false;
}
