package com.denmit99.hairbnb.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ListingSearchRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListingSearchRequest {
    String message() default "Listing search request is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
