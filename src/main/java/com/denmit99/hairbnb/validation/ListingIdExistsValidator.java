package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.repository.ListingRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ListingIdExistsValidator implements ConstraintValidator<ListingIdExists, UUID> {

    private static final String MESSAGE = "Listing with id %s does not exist";

    private final ListingRepository listingRepository;

    public ListingIdExistsValidator(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Override
    public boolean isValid(UUID listingId, ConstraintValidatorContext context) {
        if (listingId == null) {
            return true;
        }
        if (!listingRepository.existsById(listingId)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format(MESSAGE, listingId))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
