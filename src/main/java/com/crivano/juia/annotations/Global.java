package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Global {
	public enum Gender {
		HE, SHE, IT
	}

	String singular();

	String plural();

	Gender gender();

	String locator() default "";
}
