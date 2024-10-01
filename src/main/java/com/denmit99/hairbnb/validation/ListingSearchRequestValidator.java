package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.model.dto.ListingSearchRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ListingSearchRequestValidator
        implements ConstraintValidator<ListingSearchRequest, ListingSearchRequestDTO> {

    private static final String MESSAGE = "Currency is required when either minPrice or maxPrice is provided";

    @Override
    public boolean isValid(ListingSearchRequestDTO requestDTO, ConstraintValidatorContext context) {
        if ((requestDTO.getMinPrice() != null || requestDTO.getMaxPrice() != null)
                && requestDTO.getCurrency() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MESSAGE)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
