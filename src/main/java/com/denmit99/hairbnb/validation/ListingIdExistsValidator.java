package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.repository.ListingRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListingIdExistsValidator implements ConstraintValidator<ListingIdExists, Long> {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public boolean isValid(Long listingId, ConstraintValidatorContext context) {
        return listingRepository.existsById(listingId);
    }
}
