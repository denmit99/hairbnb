package com.denmit99.hairbnb.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AmenityCodesExistValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AmenityCodesExist {
    String message() default "Invalid amenity code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
