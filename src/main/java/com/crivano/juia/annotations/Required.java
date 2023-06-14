package com.crivano.juia.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Generates a required="true" even if the @NotNull is not 
// present. It is necessary because there are certain situations 
// when the field is mapped to a table that has joined subclasses. 
// So it is not possible to declare a @NotNull field, but you may 
// still want that field to be required at the user interface.
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {
}
