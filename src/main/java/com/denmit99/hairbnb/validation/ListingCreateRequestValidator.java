package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.model.dto.ListingCreateRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ListingCreateRequestValidator
        implements ConstraintValidator<ListingCreateRequest, ListingCreateRequestDTO> {
    @Override
    public boolean isValid(ListingCreateRequestDTO listingCreateRequestDTO,
                           ConstraintValidatorContext constraintValidatorContext) {
        int sum = listingCreateRequestDTO.getBedrooms().stream()
                .mapToInt(b -> Optional.ofNullable(b.getSingleNum()).orElse(0)
                        + Optional.ofNullable(b.getDoubleNum()).orElse(0) * 2
                        + Optional.ofNullable(b.getSofaNum()).orElse(0) * 2
                        + Optional.ofNullable(b.getQueenNum()).orElse(0) * 2)
                .sum();
        //The number of sleeping places should be sufficient for all guests
        return listingCreateRequestDTO.getMaxGuests() <= sum;
    }
}
