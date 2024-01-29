package com.neelav.patienthub.exceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = BloodGroupValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface BloodGroupConstraint {
    String message() default "Entered blood group type is not valid !!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
