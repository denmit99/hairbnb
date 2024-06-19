package com.denmit99.hairbnb.validation;

import com.denmit99.hairbnb.model.entity.Amenity;
import com.denmit99.hairbnb.repository.AmenityRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AmenityCodesExistValidator implements ConstraintValidator<AmenityCodesExist, Set<String>> {

    private static final String MESSAGE = "Invalid amenity codes: %s";

    @Autowired
    private AmenityRepository amenityRepository;

    @Override
    public boolean isValid(Set<String> amenityCodes, ConstraintValidatorContext context) {
        var existingAmenityCodes = amenityRepository.getAllByCodeIn(amenityCodes)
                .stream()
                .map(Amenity::getCode)
                .toList();
        if (existingAmenityCodes.size() == amenityCodes.size()) {
            return true;
        }
        var invalidCodes = new HashSet<>(amenityCodes);
        invalidCodes.removeAll(Set.copyOf(existingAmenityCodes));
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format(MESSAGE, invalidCodes))
                .addConstraintViolation();
        return false;
    }
}
