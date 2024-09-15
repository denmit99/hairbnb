package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.model.dto.BedroomDTO;
import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListingCreateRequestValidatorTest {

    private final ListingCreateRequestValidator validator = new ListingCreateRequestValidator();

    @Test
    public void guestsMoreThanPlaces() {
        var req = new ListingCreateRequestDTO();
        req.setMaxGuests(4);
        req.setBedrooms(List.of(BedroomDTO.builder()
                .singleNum(1)
                .queenNum(1)
                .build()));
        var ctx = Mockito.mock(ConstraintValidatorContext.class);
        var res = validator.isValid(req, ctx);
        assertFalse(res);
    }

    @Test
    public void guestsLessThanPlaces() {
        var req = new ListingCreateRequestDTO();
        req.setMaxGuests(2);
        req.setBedrooms(List.of(BedroomDTO.builder()
                .singleNum(1)
                        .queenNum(1)
                .build()));
        var ctx = Mockito.mock(ConstraintValidatorContext.class);
        var res = validator.isValid(req, ctx);
        assertTrue(res);
    }
}
