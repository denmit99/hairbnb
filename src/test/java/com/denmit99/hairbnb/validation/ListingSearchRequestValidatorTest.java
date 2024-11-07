package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.model.dto.ListingSearchRequestDTO;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ListingSearchRequestValidatorTest {
    private final ListingSearchRequestValidator validator = new ListingSearchRequestValidator();

    @Test
    void noCurrency_ReturnsFalse() {
        var req = new ListingSearchRequestDTO();
        req.setMaxPrice(RandomUtils.nextLong());
        var ctx = Mockito.mock(ConstraintValidatorContext.class);
        var builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(ctx.buildConstraintViolationWithTemplate(any())).thenReturn(builder);

        assertFalse(validator.isValid(req, ctx));
    }

    @Test
    void minPriceGreaterThanMaxPrice_ReturnsFalse() {
        var req = new ListingSearchRequestDTO();
        req.setMinPrice(RandomUtils.nextLong());
        req.setMaxPrice(req.getMinPrice() - 1);
        var ctx = Mockito.mock(ConstraintValidatorContext.class);
        var builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        when(ctx.buildConstraintViolationWithTemplate(any())).thenReturn(builder);

        assertFalse(validator.isValid(req, ctx));
    }

    @Test
    void isValid() {
        var req = new ListingSearchRequestDTO();
        var ctx = Mockito.mock(ConstraintValidatorContext.class);

        assertTrue(validator.isValid(req, ctx));
    }
}
